package backend;

import backend.control.ScopedStorage;
import backend.control.VariableDefinitionNode;
import backend.control.VariableNode;
import backend.error_handling.IllegalSyntaxException;
import backend.error_handling.ProjectBuildException;
import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedCommandException;
import backend.error_handling.VariableArgumentsException;
import backend.math.ConstantNode;
import backend.turtle.TurtleFactory;
import utilities.CommandGetter;
import utilities.PeekingIterator;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import backend.control.DoTimesNode;
import backend.control.LoopNode;
import backend.control.RepeatNode;
import backend.control.VariableDefinitionNode;

public class Parser {

	public static final String DELIMITER_REGEX = "\\s+";
	public static final String STANDARD_DELIMITER = " ";
	public static final String NUMBER_REGEX = "-?[0-9]+\\.?[0-9]*";
	public static final String VARIABLE_ARGS_START_DELIMITER = "(";
	public static final String VARIABLE_ARGS_END_DELIMITER = ")";

	private CommandGetter commandGetter;
	private Map<String, SyntaxNode> syntaxTrees; // cache of parsed commands
	private TurtleFactory turtleManager;
	private ScopedStorage scopedStorage;

	public Parser(TurtleFactory turtleManager) {
		commandGetter = new CommandGetter();
		syntaxTrees = new HashMap<>();
		scopedStorage = new ScopedStorage();
	}

	public boolean validateCommand(String command) throws SLogoException {
		if (command == null) {
			return false;
		}
		// Avoid repeated computation for just differing whitespace
		String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
		syntaxTrees.put(formattedCommand,
				constructSyntaxTree(new PeekingIterator<>(Arrays.asList(command.split(DELIMITER_REGEX)).iterator())));
		System.out.println("Validated command!");
		return true;
	}

	public void executeCommand(String command) throws SLogoException {
		String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
		if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
			syntaxTrees.put(formattedCommand, constructSyntaxTree(
					new PeekingIterator<String>(Arrays.asList(command.split(DELIMITER_REGEX)).iterator())));
		}
		SyntaxNode tree = syntaxTrees.get(formattedCommand);
		tree.execute();
	}

	// To support switching of language through front end
	public void setLanguage(String language) {
		try {
			commandGetter.setLanguage(language);
		} catch (IOException e) {
			// Can only be because language passed in is not supported
			SLogoException badLanguage = new ProjectBuildException();
			badLanguage.getMessage();
		}
	}

	// Top-Level parsing command that can add disjoint commands to root
	private SyntaxNode constructSyntaxTree(PeekingIterator<String> it) throws SLogoException {
		if (it == null) {
			throw new IllegalArgumentException();
		}
		RootNode rootNode = new RootNode();
		while (it.hasNext()) {
			// System.out.println("Adding a child to root");
			rootNode.addChild(makeExpTree(it));
		}
		System.out.println("Constructed Syntax Tree");
		return rootNode;
	}

	// Parsing command which chooses appropriate parsing command for
	// construction of next node
	private SyntaxNode makeExpTree(PeekingIterator<String> it) throws SLogoException {
		// System.out.println("Making expTree");
		if (!it.hasNext()) { // Done parsing
			return null;
		}
		String nextToken = it.peek();
		System.out.println("Next token: " + nextToken);
		if (nextToken.equals(VARIABLE_ARGS_START_DELIMITER)) {
			it.next();
			return makeExpTreeForVariableParameters(it);
		}
		if (isNumeric(nextToken)) {
			it.next();
			System.out.println("Numeric, making ConstantNode");
			return new ConstantNode(Double.parseDouble(nextToken));
		}
		System.out.println("Not numeric");
		// Need to check for user-declared methods here
		// TODO - check if variable exists using Ben's new variableExists() method
		if (scopedStorage.existsVariable(nextToken)) {
			it.next();
			return new VariableNode(scopedStorage, nextToken);
		}
		// Dispatch appropriate method
		try {
			System.out.println("Retrieving appropriate parsing method");
			Method nextParsingMethod = commandGetter.getParsingMethod(nextToken);
			System.out.println("Next parsing method: " + nextParsingMethod.getName());
			return (SyntaxNode) nextParsingMethod.invoke(this, it);
		} catch (IllegalAccessException | InvocationTargetException badCommand) {
			badCommand.printStackTrace();
			throw new UndefinedCommandException(nextToken);
		}

	}

	private ValueNode makeValueNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making a ValueNode");
		if (it == null || !it.hasNext()) {
			throw new IllegalArgumentException();
		}
		String commandName = it.next();
		// Use reflection to invoke right command constructor
		System.out.println("Getting appropriate command constructor");
		try {
			Class commandClass = commandGetter.getCommandNodeClass(commandName);
			System.out.println("Command class: " + commandClass.getName());
			ValueNode valueNode = (ValueNode) commandClass.getConstructor(null).newInstance();
			int numChildren = valueNode.getDefaultNumberOfArguments();
			System.out.println("No. of children: " + numChildren);
			SyntaxNode nextChild;
			for (int child = 0; child < numChildren; child++) {
				System.out.println("Preparing to append child to ValueNode");
				nextChild = makeExpTree(it);
				valueNode.addChild(nextChild);
				System.out.println("Added child no. " + child + " to ValueNode");
			}
			return valueNode;
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| InstantiationException e) {
			throw new UndefinedCommandException(commandName);
		}
	}

	private VariableDefinitionNode makeVariableDefinitionNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making VariableDefinitionNode");
		// Consume the MAKE / SET token
		it.next();
		// Extract the variable name
		String varName = it.next();
		// Resolve the expression into a tree
		SyntaxNode expr = makeExpTree(it);
		return new VariableDefinitionNode(scopedStorage, varName, expr);
	}

	private RepeatNode makeRepeatNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making RepeatNode");
		// Consume the REPEAT token
		it.next();
		SyntaxNode numberOfTimesToRepeat = makeExpTree(it);
		SyntaxNode exprToRepeat = makeExpTree(it);
		return new RepeatNode(scopedStorage, numberOfTimesToRepeat, exprToRepeat);
	}

	private DoTimesNode makeDoTimesNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making DoTimesNode");
		// Consume the DOTIMES token
		it.next();
		String varName = it.next();
		SyntaxNode limitExp = makeExpTree(it);
		SyntaxNode exprToRepeat = makeExpTree(it);
		// Error will be resolved when limit arg type is changed to SyntaxNode in
		// DoTimesNode constructor
		return new DoTimesNode(scopedStorage, varName, limitExp, exprToRepeat);
	}

	private LoopNode makeForLoopNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making a ForLoopNode");
		// Consume the FOR token
		it.next();
		String varName = it.next();
		SyntaxNode startExp = makeExpTree(it);
		SyntaxNode endExp = makeExpTree(it);
		SyntaxNode incrExp = makeExpTree(it);
		SyntaxNode exprToRepeat = makeExpTree(it);
		return new LoopNode(scopedStorage, varName, startExp, endExp, incrExp, exprToRepeat);
	}

	private IfNode makeIfNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making an IfNode");
		// Consume the IF token
		it.next();
		SyntaxNode conditionExpression = makeExpTree(it);
		SyntaxNode trueBranch = makeExpTree(it);
		return new IfNode(conditionExpression, trueBranch);
	}
	
	private IfElseNode makeIfElseNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making an IfElseNode");
		// Consume the IfELSE token
		it.next();
		SyntaxNode conditionExpression = makeExpTree(it);
		SyntaxNode trueBranch = makeExpTree(it);
		SyntaxNode elseBranch = makeExpTree(it);
		return new IfElseNode(conditionExpression, trueBranch, elseBranch);
	}
	
	private FunctionDefinitionNode makeFunctionDefinitionNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making a FunctionDefinitionNode");
		// Consume the MAKEUSERINSTRUCTION token
		it.next();
		String funcName = it.next();
		SyntaxNode funcRoot = makeExpTree(it);
		return new FunctionDefinitionNode(scopedStorage, funcName, funcRoot);
	}
	
	// Only ValueNodes can have variable params
	private ValueNode makeExpTreeForVariableParameters(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making expTree for variable params");
		if (it == null || !it.hasNext()) {
			throw new IllegalArgumentException();
		}
		// Retrive just the root node corresponding to this command
		// it advanced by one token
		String commandName = it.peek();
		ValueNode root = makeValueNode(it);
		// TODO
		if (root == null || !root.canTakeVariableNumberOfArguments()) {
			throw new VariableArgumentsException(commandName);
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
