package backend;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import commands.IterationCommand;
import commands.NewCommand;
import utilities.CommandGetter;
import commands.AbstractCommand;
import commands.Constant;
import commands.ControlCommand;
import utilities.Reflector;

public class Parser {

	public static final String DELIMITER_REGEX = "\\s+";
	public static final String STANDARD_DELIMITER = " ";
	public static final String NUMBER_REGEX = "-?[0-9]+\\.?[0-9]*";
	public static final String VARIABLE_ARGS_START_DELIMITER = "(";
	public static final String VARIABLE_ARGS_END_DELIMITER = ")";

	private CommandGetter commandGetter;
	private Map<String, SyntaxNode> syntaxTrees; // cache of parsed commands
	private FunctionsStore functionsStore;

	public Parser() {
		commandGetter = new CommandGetter();
		syntaxTrees = new HashMap<>();
		functionsStore = new FunctionsStore();
	}

	public boolean validateCommand(String command) {
		if (command == null) {
			return false;
		}
		// Avoid repeated computation for just differing whitespace
		String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
		try {
			syntaxTrees.put(formattedCommand, makeExpTree(command.split(DELIMITER_REGEX), 0));
			return true;
		} catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException
				| InstantiationException e) {
			return false;
		}
	}

	public void executeCommand(String command) throws IllegalArgumentException {
		try {
			String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
			if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
				syntaxTrees.put(formattedCommand, makeExpTree(command.split(DELIMITER_REGEX), 0));
			}
			SyntaxNode tree = syntaxTrees.get(formattedCommand);
			tree.execute();
		} catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException
				| InstantiationException e) {
			throw new IllegalArgumentException();
		}
	}

	// To support switching of language through front end
	public void setLanguage(String language) {
		commandGetter.setLanguage(language);
	}

	private SyntaxNode makeExpTree(String[] commands, int index)
			throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException,
			InvocationTargetException, IllegalAccessException {
		if (commands == null) {
			throw new IllegalArgumentException();
		}
		if (index >= commands.length) {
			return null;
		}
		SyntaxNode root;
		if (commands[index].equals(VARIABLE_ARGS_START_DELIMITER)) {
			int variableArgsCommandEndIndex = findVariableArgsEndpoint(commands, index);
			return makeExpTreeForVariableParameters(commands, index + 1, variableArgsCommandEndIndex);
		}
		root = makeSyntaxNodeForCommand(commands[index]);
		AbstractCommand rootCommand = root.getCommand();
		int numChildren = rootCommand.getNumberOfArguments();
		index++;
		SyntaxNode nextChild;
		for (int child = 0; child < numChildren; child++) {
			nextChild = makeExpTree(commands, index);
			root.addChild(nextChild);
			index += getTokensConsumed(nextChild);
		}
		return root;
	}

	/**
	 * My new idea is to use reflection to choose which method to make nodes with (e.g. "for" will lead to this)
	 * @param tokens
	 * @param index
	 * @return
	 */
	private ControlNode makeForLoopNode(String[] tokens, int index)
			throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException,
			InvocationTargetException, IllegalAccessException {
		// todo - dont use assert, do something better
		assert tokens[++index].equals("[");
		String indexVariableName = tokens[++index];
		double start = Double.parseDouble(tokens[++index]),
				end = Double.parseDouble(tokens[++index]),
				increment = Double.parseDouble(tokens[++index]);
		assert tokens[++index].equals("]");
		assert tokens[++index].equals("[");
		functionsStore.storeVariable(indexVariableName, start);
		Entry<String, Double> indexVariable = functionsStore.getVariable(indexVariableName);
		SyntaxNode subtree = makeExpTree(tokens, index);
		//Method forMethod = commandGetter.getCommand)
		ControlCommand forCommand = new IterationCommand(null, indexVariable, end, increment, subtree);
		//return new ControlNode(forCommand);
		return null;
	}

	private SyntaxNode makeSyntaxNodeForCommand(String commandName) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		if (isNumeric(commandName)) {
			// TODO - change below (just a temp fix to make things compile with my (Ben)
			// changes)
			Method methodToInvoke = Constant.class.getDeclaredMethod("getValue");
			Constant constant = new Constant(methodToInvoke, Double.parseDouble(commandName));
			return new ValueNode(constant);
		}
		// TODO - Check variable store for user-defined variables first
		
		// Account for localization
		AbstractCommand command = commandGetter.getCommandFromName(commandName.toLowerCase());
		// Make either ValueNode or CommandNode based on info about the commandName
		SyntaxNode root = command instanceof ControlCommand ? new ControlNode((ControlCommand) command)
				: new ValueNode(command);
		return root;
	}

	private SyntaxNode makeExpTreeForVariableParameters(String[] commands, int startIndex, int endIndex)
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException,
			ClassNotFoundException {
		if (commands == null || startIndex >= commands.length || startIndex > endIndex || endIndex >= commands.length) {
			throw new IllegalArgumentException();
		}
		SyntaxNode root = makeSyntaxNodeForCommand(commands[startIndex]);
		if (!root.getCommand().takesVariableArguments()) {
			throw new IllegalArgumentException();
		}
		int index = startIndex + 1;
		SyntaxNode nextChild;
		while (index < endIndex) {
			nextChild = makeExpTree(commands, index);
			root.addChild(nextChild);
			index += getTokensConsumed(nextChild);
		}
		root.setHasVariableArgs(true);
		return root;
	}

	private boolean isNumeric(String command) {
		return command != null && command.matches(NUMBER_REGEX);
	}

	private int findVariableArgsEndpoint(String[] commands, int startIndex) throws IllegalArgumentException {
		if (commands == null || startIndex < 0 || startIndex >= commands.length) {
			throw new IllegalArgumentException();
		}
		int indentation = 0;
		int index = startIndex;
		while (index < commands.length) {
			if (commands[index].equals(VARIABLE_ARGS_START_DELIMITER)) {
				indentation++;
			} else if (commands[index].equals(VARIABLE_ARGS_END_DELIMITER)) {
				if (--indentation == 0) {
					return index;
				}
			}
		}
		throw new IllegalArgumentException();
	}

	private int getTokensConsumed(SyntaxNode root) {
		return root.hasVariableArgs() ? root.getSize() + 2 : root.getSize();
	}

	// Move to either utilities or to AbstractCommand as static method?

}
