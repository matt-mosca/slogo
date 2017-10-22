package backend;

import deprecated_commands.AbstractCommand;
import deprecated_commands.Constant;
import deprecated_commands.ControlCommand;
import deprecated_commands.IterationCommand;
import utilities.CommandGetter;
import utilities.PeekingIterator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import backend.control_nodes.IterationNode;
import backend.math_nodes.ConstantNode;

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
			syntaxTrees.put(formattedCommand,
					makeExpTree(new PeekingIterator<String>(Arrays.asList(command.split(DELIMITER_REGEX)).iterator())));
			return true;
		} catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException
				| InstantiationException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void executeCommand(String command) throws IllegalArgumentException {
		try {
			String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
			if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
				syntaxTrees.put(formattedCommand, makeExpTree(
						new PeekingIterator<String>(Arrays.asList(command.split(DELIMITER_REGEX)).iterator())));
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
		try {
			commandGetter.setLanguage(language);
		} catch (IOException e) {
			// Can only be because language passed in is not supported
			throw new IllegalArgumentException();
		}
	}

	// Top-level parsing command which chooses appropriate parsing command for
	// construction of next node
	private SyntaxNode makeExpTree(PeekingIterator<String> it) throws IllegalArgumentException, ClassNotFoundException,
			NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException {
		if (it == null) {
			throw new IllegalArgumentException();
		}
		if (!it.hasNext()) { // Done parsing
			return null;
		}
		String nextToken = it.peek();
		if (nextToken.equals(VARIABLE_ARGS_START_DELIMITER)) {
			it.next();
			return makeExpTreeForVariableParameters(it);
		}
		if (isNumeric(nextToken)) {
			return new ConstantNode(Double.parseDouble(nextToken));
		}
		// Need to check for user-declared methods here
		// TODO - check if variable exists using Ben's new variableExists() method
		if (functionsStore.getVariable(nextToken) != null) {
			return new VariableNode(functionsStore, nextToken);
		}
		// Dispatch appropriate method
		Method nextParsingMethod = commandGetter.getParsingMethod(nextToken);
		return (SyntaxNode) nextParsingMethod.invoke(this, it);
	}

	private ValueNode makeValueNode(PeekingIterator<String> it) throws IllegalArgumentException, ClassNotFoundException,
			NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException {
		if (it == null || !it.hasNext()) {
			throw new IllegalArgumentException();
		}
		String commandName = it.next();
		// Use reflection to invoke right command constructor
		Class commandClass = commandGetter.getCommandNodeClass(commandName);
		ValueNode valueNode = (ValueNode) commandClass.getConstructor(null).newInstance();
		int numChildren = valueNode.getDefaultNumberOfArguments();
		SyntaxNode nextChild;
		for (int child = 0; child < numChildren; child++) {
			nextChild = makeExpTree(it);
			valueNode.addChild(nextChild);
		}
		return valueNode;
	}

	/*
	private SyntaxNode makeSyntaxNodeForCommand(PeekingIterator<String> it) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		if (isNumeric(commandName)) {
			return new ConstantNode(Double.parseDouble(commandName));
		}
		// TODO - Check variable store for user-defined variables first
		// Account for localization
		// AbstractCommandOld command =
		// commandGetter.getCommandFromName(commandName.toLowerCase());
		Method commandParsingMethod = commandGetter.getMethodFromCommandName(commandName.toLowerCase());
		SyntaxNode root = (SyntaxNode) commandParsingMethod.invoke(this, tokens, ++index);
		// Make either ValueNode or CommandNode based on info about the commandName
		return root;
	}
	*/

	/**
	 * My new idea is to use reflection to choose which method to make nodes with
	 * (e.g. "for" will lead to this)
	 * 
	 * @param tokens
	 * @param index
	 * @return
	 */
	
	/*
	private IterationNode makeForLoopNode(PeekingIterator<String> it)
			throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException,
			InvocationTargetException, IllegalAccessException {
		// todo - dont use assert, do something better
		if (!tokens[index].equals("[")) {
			throw new IllegalArgumentException();
		}
		String indexVariableName = tokens[++index];
		double start = Double.parseDouble(tokens[++index]), end = Double.parseDouble(tokens[++index]),
				increment = Double.parseDouble(tokens[++index]);
		if (!(tokens[++index].equals("]") && tokens[++index].equals("["))) {
			throw new IllegalArgumentException();
		}
		functionsStore.setVariable(indexVariableName, start);
		Entry<String, Double> indexVariable = functionsStore.getVariable(indexVariableName);
		SyntaxNode subtree = makeExpTree(tokens, ++index);
		ControlCommand forCommand = new IterationCommand(indexVariable, end, increment, subtree);
		return new ControlNode(forCommand);
	}
	*/

	// Only ValueNodes can have variable params
	private ValueNode makeExpTreeForVariableParameters(PeekingIterator<String> it) throws NoSuchMethodException,
			InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		if (it == null || !it.hasNext()) {
			throw new IllegalArgumentException();
		}
		// Retrive just the root node corresponding to this command
		// it advanced by one token
		ValueNode root = makeValueNode(it);
		// TODO
		if (!root.canTakeVariableNumberOfArguments()) {
			throw new IllegalArgumentException();
		}
		SyntaxNode nextChild;
		while (!it.peek().equals(VARIABLE_ARGS_END_DELIMITER)) {
			nextChild = makeExpTree(it);
			root.addChild(nextChild);
		}
		// Consume ')' token
		it.next();
		return root;
	}

	private boolean isNumeric(String command) {
		return command != null && command.matches(NUMBER_REGEX);
	}

}
