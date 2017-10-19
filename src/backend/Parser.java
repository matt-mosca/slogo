package backend;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.CommandGetter;
import commands.AbstractCommand;
import commands.Constant;
import utilities.Reflector;

public class Parser {

	public static final String DELIMITER_REGEX = "\\s+";
	public static final String STANDARD_DELIMITER = " ";
	public static final String NUMBER_REGEX = "-?[0-9]+\\.?[0-9]*";

	private CommandGetter commandGetter;
	private Reflector reflector;
	private Map<String, SyntaxNode> syntaxTrees; // cache of parsed commands

	public Parser() {
		commandGetter = new CommandGetter();
		syntaxTrees = new HashMap<>();
		reflector = new Reflector();
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
			parseSyntaxTree(tree);
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
		String commandName = commands[index];
		if (isNumeric(commandName)) {
			// TODO - change below (just a temp fix to make things compile with my (Ben) changes)
			Method methodToInvoke = Constant.class.getDeclaredMethod("getValue");
			Constant constant = new Constant(methodToInvoke, Double.parseDouble(commandName));
			return new SyntaxNode(constant);
		}
		// TODO - Check variable store for user-defined variables first

		// Account for localization
		AbstractCommand command = commandGetter.getCommandFromName(commandName.toLowerCase());
		// TODO - need to update this to handle arbitrary args (as in (sum 10 10 10...) )
		int numChildren = (command.takesVariableArguments() ? 2 : command.getNumberOfArguments());
		SyntaxNode root = new SyntaxNode(command);
		index++;
		SyntaxNode nextChild;
		for (int child = 0; child < numChildren; child++) {
			nextChild = makeExpTree(commands, index);
			root.addChild(nextChild);
			index += nextChild.getSize();
		}
		return root;
	}

	// Handled differently based on type of command
	private double parseSyntaxTree(SyntaxNode tree) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (tree == null) {
			throw new IllegalArgumentException();
		}
		double[] operands = new double[tree.getChildren().size()];
		List<SyntaxNode> children = tree.getChildren();
		for (int childIndex = 0; childIndex < children.size(); childIndex++) {
			SyntaxNode child = children.get(childIndex);
			operands[childIndex] = parseSyntaxTree(child);
		}
		// FOR DEBUGGING, return directly in future without printing
		double result;
		// TODO - make this more elegant (Ben temp fix)
		if (operands.length == 0) {
			result = tree.getCommand().execute(null);
		} else if (operands.length == 1) {
			result = tree.getCommand().execute(operands[0]);
		} else if (operands.length == 2 && !tree.getCommand().takesVariableArguments()) {
			result = tree.getCommand().execute(operands[0], operands[1]);
		} else {
			result = tree.getCommand().execute(operands);
		}
		return result;
	}

	private boolean isNumeric(String command) {
		return command != null && command.matches(NUMBER_REGEX);
	}

	// Move to either utilities or to AbstractCommand as static method?


}
