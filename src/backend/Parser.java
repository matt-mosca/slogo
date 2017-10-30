package backend;

import backend.control.DoTimesNode;
import backend.control.FunctionDefinitionNode;
import backend.control.FunctionNode;
import backend.control.IfElseNode;
import backend.control.IfNode;
import backend.control.LoopNode;
import backend.control.RepeatNode;
import backend.control.ScopedStorage;
import backend.control.VariableDefinitionNode;
import backend.control.VariableNode;
import backend.error_handling.IllegalSyntaxException;
import backend.error_handling.ProjectBuildException;
import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedCommandException;
import backend.error_handling.VariableArgumentsException;
import backend.math.ConstantNode;
import backend.turtle.AskNode;
import backend.turtle.AskWithNode;
import backend.turtle.TellNode;
import backend.turtle.TurtleFactory;
import backend.turtle.TurtleNode;
import backend.view_manipulation.ViewController;
import backend.view_manipulation.ViewNode;
import utilities.CommandGetter;
import utilities.PeekingIterator;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

	public static final String DELIMITER_REGEX = "\\s+";
	public static final String STANDARD_DELIMITER = " ";
	public static final String NUMBER_REGEX = "-?[0-9]+\\.?[0-9]*";
	public static final String VARIABLE_REGEX = ":[a-zA-Z_]+";
	public static final String VARIABLE_ARGS_START_DELIMITER = "(";
	public static final String VARIABLE_ARGS_END_DELIMITER = ")";
	public static final String LIST_START_DELIMITER = "[";
	public static final String LIST_END_DELIMITER = "]";

	public static final String TURTLE_PACKAGE = "backend.turtle";

	private CommandGetter commandGetter;
	private Map<String, SyntaxNode> syntaxTrees; // cache of parsed commands
	private TurtleFactory turtleManager;
	private ScopedStorage scopedStorage;
	private ViewController viewController;

	public Parser(TurtleFactory turtleManager, ScopedStorage storage, ViewController viewController) {
		commandGetter = new CommandGetter();
		syntaxTrees = new HashMap<>();
		scopedStorage = storage;
		this.turtleManager = turtleManager;
		this.viewController = viewController;
	}

	public boolean validateCommand(String command) throws SLogoException {
		if (command == null) {
			return false;
		}
		// Avoid repeated computation for just differing whitespace
		String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER).trim();
		System.out.println("Formatted command: " + formattedCommand);
		syntaxTrees.put(formattedCommand,
				constructSyntaxTree(new PeekingIterator<>(Arrays.asList(formattedCommand.split(DELIMITER_REGEX)).iterator())));
		System.out.println("Validated command!");
		System.out.println("Serializing Tree: ");
		//System.out.println(serializeTree(syntaxTrees.get(formattedCommand)));
		return true;
	}

	public void executeCommand(String command) throws SLogoException {
		String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER).trim();
		if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
			syntaxTrees.put(formattedCommand, constructSyntaxTree(
					new PeekingIterator<String>(Arrays.asList(formattedCommand.split(DELIMITER_REGEX)).iterator())));
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
			rootNode.addChild(makeExpTree(it));
		}
		System.out.println("Constructed Syntax Tree");
		return rootNode;
	}

	// Parsing command which chooses appropriate parsing command for
	// construction of next node
	private SyntaxNode makeExpTree(PeekingIterator<String> it) throws SLogoException {
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
		// Need to check for user-declared methods here
		if (scopedStorage.existsFunction(nextToken)) {
			return makeFunctionNode(it);
		}
		if (scopedStorage.existsVariable(nextToken) || nextToken.matches(VARIABLE_REGEX)) {
			it.next();
			return new VariableNode(nextToken, scopedStorage, nextToken);
		}
		// Dispatch appropriate method
		try {
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
			Constructor constructor;
			Object[] constructorArgs;
			if (isTurtleNode(commandClass)) {
				constructor = commandClass.getConstructor(String.class, TurtleFactory.class);
				constructorArgs = new Object[] { commandName, turtleManager };
			}
			// Note - I added this so I can test the commands (Ben), free to change as you
			// see fit
			else if (isViewNode(commandClass)) {
				constructor = commandClass.getConstructor(String.class, ViewController.class);
				constructorArgs = new Object[] { commandName, viewController };
			} else {
				System.out.println("Making normal valuenode");
				constructor = commandClass.getConstructor(String.class);
				constructorArgs = new Object[] {commandName};
			}
			System.out.print("Found constructor");
			ValueNode valueNode = (ValueNode) constructor.newInstance(constructorArgs);
			int numChildren = valueNode.getDefaultNumberOfArguments();
			System.out.println("No. of children: " + numChildren);
			SyntaxNode nextChild;
			for (int child = 0; child < numChildren; child++) {
				nextChild = makeExpTree(it);
				valueNode.addChild(nextChild);
			}
			return valueNode;
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| InstantiationException e) {
			e.printStackTrace();
			throw new UndefinedCommandException(commandName);
		}
	}

	private VariableDefinitionNode makeVariableDefinitionNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making VariableDefinitionNode");
		// Consume the MAKE / SET token
		String token = it.next();
		// Extract the variable name
		String varName = it.next();
		// Assert proper regex
		if (!varName.matches(VARIABLE_REGEX)) {
			throw new IllegalSyntaxException(varName);
		}
		// Resolve the expression into a tree
		SyntaxNode expr = makeExpTree(it);
		// TODO - make multiple vars ( make :a 10 :b 9 ... ) work
		return new VariableDefinitionNode(token, scopedStorage, new String[] { varName }, new SyntaxNode[] { expr });
	}

	private FunctionNode makeFunctionNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making FunctionNode");
		// Consume the function name
		String funcName = it.next();
		// Need to know number of arguments taken by function
		int numberOfFunctionParameters = scopedStorage.getNumberOfFunctionParameters(funcName);
		System.out.println("Number of function parameters: " + numberOfFunctionParameters);
		List<SyntaxNode> functionParameters = new ArrayList<>();
		for (int parameterIndex = 0; parameterIndex < numberOfFunctionParameters; parameterIndex++) {
			functionParameters.add(makeExpTree(it));
		}
		return new FunctionNode(funcName, scopedStorage, funcName, functionParameters);
	}

	private RepeatNode makeRepeatNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making RepeatNode");
		// Consume the REPEAT token
		String token = it.next();
		SyntaxNode numberOfTimesToRepeat = makeExpTree(it);
		RootNode commandsListRoot = getCommandsListRoot(it);
		return new RepeatNode(token, scopedStorage, numberOfTimesToRepeat, commandsListRoot);
	}

	private DoTimesNode makeDoTimesNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making DoTimesNode");
		// Consume the DOTIMES token
		String token = it.next();
		// Consume the '[' token
		String listStartToken = it.next();
		if (!listStartToken.equals(LIST_START_DELIMITER)) {
			throw new IllegalSyntaxException(LIST_START_DELIMITER);
		}
		String varName = it.next();
		SyntaxNode limitExp = makeExpTree(it);
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(LIST_END_DELIMITER);
		}
		// Consume ']' token
		it.next();
		RootNode commandsListRoot = getCommandsListRoot(it);
		// Error will be resolved when limit arg type is changed to SyntaxNode in
		// DoTimesNode constructor
		return new DoTimesNode(token, scopedStorage, varName, limitExp, commandsListRoot);
	}

	private LoopNode makeForLoopNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making a ForLoopNode");
		// Consume the FOR token
		String token = it.next();
		// Consume the '[' token
		String listStartToken = it.next();
		if (!listStartToken.equals(LIST_START_DELIMITER)) {
			throw new IllegalSyntaxException(LIST_START_DELIMITER);
		}
		String varName = it.next();
		SyntaxNode startExp = makeExpTree(it);
		SyntaxNode endExp = makeExpTree(it);
		SyntaxNode incrExp = makeExpTree(it);
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(LIST_END_DELIMITER);
		}
		// Consume ']' token
		it.next();
		RootNode commandsListRoot = getCommandsListRoot(it);
		return new LoopNode(token, scopedStorage, varName, startExp, endExp, incrExp, commandsListRoot);
	}

	private IfNode makeIfNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making an IfNode");
		// Consume the IF token
		String token = it.next();
		SyntaxNode conditionExpression = makeExpTree(it);
		RootNode commandsListRoot = getCommandsListRoot(it);
		return new IfNode(token, scopedStorage, conditionExpression, commandsListRoot);
	}

	private IfElseNode makeIfElseNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making an IfElseNode");
		// Consume the IfELSE token
		String token = it.next();
		SyntaxNode conditionExpression = makeExpTree(it);
		RootNode trueCommandsListRoot = getCommandsListRoot(it);
		RootNode falseCommandsListRoot = getCommandsListRoot(it);
		return new IfElseNode(token, scopedStorage, conditionExpression, trueCommandsListRoot, falseCommandsListRoot);
	}

	// TODO - SPLIT INTO SMALLER HELPERS
	private FunctionDefinitionNode makeFunctionDefinitionNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making a FunctionDefinitionNode");
		List<String> funcStrings = new ArrayList<>();
		// Consume the MAKEUSERINSTRUCTION token
		String token = it.next();
		funcStrings.add(token);
		String funcName = it.next();
		funcStrings.add(funcName);
		// To support recursive functions, may need to store the funcName here
		scopedStorage.registerFunctionName(funcName);
		String listStartToken = it.next();
		if (!listStartToken.equals(LIST_START_DELIMITER)) {
			throw new IllegalSyntaxException(LIST_START_DELIMITER);
		}
		List<String> variableNames = new ArrayList<>();
		while (it.hasNext() && !it.peek().equals(LIST_END_DELIMITER)) {
			String variableName = it.next();
			if (!variableName.matches(VARIABLE_REGEX)) {
				throw new IllegalSyntaxException(variableName);
			}
			variableNames.add(variableName);
		}
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(LIST_END_DELIMITER);
		}
		// Consume the ']' token
		it.next();
		RootNode funcRoot = getCommandsListRoot(it);
		// TODO - Save string to ScopedStorage for future loading of function
		return new FunctionDefinitionNode(token, scopedStorage, funcName, funcRoot, variableNames);
	}

	private TellNode makeTellNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making TellNode");
		// Consume the TELL token
		String token = it.next();
		RootNode idsRoot = getCommandsListRoot(it);
		TellNode tellNode = new TellNode(token, turtleManager);
		// TODO - use helper for this??
		for (SyntaxNode child : idsRoot.getChildren()) {
			tellNode.addChild(child);
		}
		return tellNode;
	}

	private AskNode makeAskNode(PeekingIterator<String> it) throws SLogoException {
		System.out.println("Making AskNode");
		// Consume the ASK button
		String token = it.next();
		RootNode idsRoot = getCommandsListRoot(it);
		RootNode commandsListRoot = getCommandsListRoot(it);
		AskNode askNode = new AskNode(token, turtleManager, commandsListRoot);
		for (SyntaxNode child : idsRoot.getChildren()) {
			askNode.addChild(child);
		}
		return askNode;
	}

	private AskWithNode makeAskWithNode(PeekingIterator<String> it) throws SLogoException {
		System.out.print("Making AskWithNode");
		// Consume the ASKWITH button
		String token = it.next();
		RootNode queriesRoot = getCommandsListRoot(it);
		RootNode commandsListRoot = getCommandsListRoot(it);
		AskWithNode askWithNode = new AskWithNode(token, turtleManager, queriesRoot, commandsListRoot);
		return askWithNode;
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
		while (it.hasNext() && !it.peek().equals(VARIABLE_ARGS_END_DELIMITER)) {
			nextChild = makeExpTree(it);
			root.addChild(nextChild);
		}
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(VARIABLE_ARGS_END_DELIMITER);
		}
		// Consume ')' token
		it.next();
		return root;
	}

	// Called when expecting a list
	private RootNode getCommandsListRoot(PeekingIterator<String> it) throws SLogoException {
		if (it == null || !it.hasNext()) {
			throw new IllegalArgumentException();
		}
		String listStartToken = it.next();
		if (!listStartToken.equals(LIST_START_DELIMITER)) {
			throw new IllegalSyntaxException(listStartToken);
		}
		RootNode commandsListRoot = new RootNode();
		while (it.hasNext() && !it.peek().equals(LIST_END_DELIMITER)) {
			commandsListRoot.addChild(makeExpTree(it));
		}
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(LIST_END_DELIMITER);
		}
		// Consume ']' token
		it.next();
		return commandsListRoot;
	}

	private boolean isNumeric(String command) {
		return command != null && command.matches(NUMBER_REGEX);
	}

	// TODO - Helper function for all these?
	private boolean isTurtleNode(Class nodeClass) {
		return TurtleNode.class.isAssignableFrom(nodeClass);
	}

	private boolean isViewNode(Class nodeClass) {
		return ViewNode.class.isAssignableFrom(nodeClass);
	}

	private boolean isValueNode(Class nodeClass) {
		return ValueNode.class.isAssignableFrom(nodeClass);
	}

	// Special cases in serialization
	private boolean isConstantNode(Class nodeClass) {
		return ConstantNode.class.isAssignableFrom(nodeClass);
	}

	private boolean isVariableNode(Class nodeClass) {
		return VariableNode.class.isAssignableFrom(nodeClass);
	}

	private boolean isFunctionDefinitionNode(Class nodeClass) {
		return FunctionDefinitionNode.class.isAssignableFrom(nodeClass);
	}

	private boolean isDoTimesNode(Class nodeClass) {
		return DoTimesNode.class.isAssignableFrom(nodeClass);
	}
	
	private boolean isRootNode(Class nodeClass) {
		return RootNode.class.isAssignableFrom(nodeClass);
	}

	/*
	
	// TODO - Move to debugger?
	public String serializeTree(SyntaxNode root) throws SLogoException {
		if (root == null) {
			return "";
		}
		if (isConstantNode(root.getClass())) {
			return Double.toString(((ConstantNode) root).getValue());
		}
		// Dispatch appropriate method
		try {
			Method nextSerializingMethod = commandGetter.getSerializingMethod(root.getClass());
			System.out.println("Next serializing method: " + nextSerializingMethod.getName());
			return (String) nextSerializingMethod.invoke(this, root);
		} catch (IllegalAccessException | InvocationTargetException badCommand) {
			badCommand.printStackTrace();
			throw new UndefinedCommandException(root.getClass().getName());
		}
	}
	
	public String serializeValueNode(SyntaxNode root) throws SLogoException {
		if (root == null) {
			return "";
		}
		if (!isValueNode(root.getClass())) {
			throw new IllegalArgumentException();
		}
		String rootString = "";
		ValueNode valueNode = (ValueNode) root;
		boolean isTakingUnlimitedParams = false;
		List<SyntaxNode> children = valueNode.getChildren();
		if (!isRootNode(valueNode.getClass())) {
			// Check for unlimited params - if so, need to add a '(' and ')'
			if (valueNode.canTakeVariableNumberOfArguments()
					&& children.size() != valueNode.getDefaultNumberOfArguments()) {
				isTakingUnlimitedParams = true;
				rootString += VARIABLE_ARGS_START_DELIMITER + STANDARD_DELIMITER;
			}
			rootString += commandGetter.getNameFromCommandClass(root.getClass());
		}
		for (SyntaxNode child : valueNode.getChildren()) {
			rootString += STANDARD_DELIMITER + serializeTree(child);
		}
		if (isTakingUnlimitedParams) {
			rootString += STANDARD_DELIMITER + VARIABLE_ARGS_END_DELIMITER;
		}
		return rootString;
	}

	public String serializeDoTimesNode(SyntaxNode root) throws SLogoException {
		if (root == null) {
			return "";
		}
		if (!(root instanceof DoTimesNode)) {
			// TODO - need custom SLogoException for serialization?
			throw new IllegalArgumentException();
		}
		DoTimesNode doTimesNode = (DoTimesNode) root;
		String rootString = commandGetter.getNameFromCommandClass(root.getClass());
		String iterationVariableString = doTimesNode.getIterationVariable();
		String endExpressionString = serializeTree(doTimesNode.getEndExpression());
		String commandString = serializeTree(doTimesNode.getCommandSubtree());
		return rootString + STANDARD_DELIMITER + LIST_START_DELIMITER + STANDARD_DELIMITER + iterationVariableString
				+ STANDARD_DELIMITER + endExpressionString + STANDARD_DELIMITER + LIST_END_DELIMITER
				+ STANDARD_DELIMITER + LIST_START_DELIMITER + STANDARD_DELIMITER + commandString + STANDARD_DELIMITER
				+ LIST_END_DELIMITER;
	}
	*/
}
