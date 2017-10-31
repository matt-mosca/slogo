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
import backend.error_handling.SLogoException;
import backend.error_handling.UnbalancedArgumentsException;
import backend.error_handling.UnbalancedMakeException;
import backend.error_handling.UndefinedCommandException;
import backend.error_handling.VariableArgumentsException;
import backend.math.ConstantNode;
import backend.turtle.AskNode;
import backend.turtle.AskWithNode;
import backend.turtle.TellNode;
import backend.turtle.TurtleController;
import backend.view_manipulation.ViewController;
import utilities.CommandGetter;
import utilities.PeekingIterator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Entity that validates, parses and executes raw String commands Trees are
 * built pre-order and executed post-order through mutually recursive functions
 * 
 * @author Adithya Raghunathan
 */
public class Parser {

	private CommandGetter commandGetter;
	private Map<String, SyntaxNode> syntaxTrees; // cache of parsed commands
	private List<SyntaxNode> commandHistory; // for undo/redo
	private TurtleController turtleManager;
	private ScopedStorage scopedStorage;
	private ViewController viewController;
	private ParserUtils parserUtils;

	private int undoIndex;

	/**
	 * Initialize a parser with references to a TurtleController, ScopedStorage,
	 * ViewController, CommandGetter and ParserUtils
	 */
	public Parser(TurtleController turtleManager, ScopedStorage storage, ViewController viewController,
			CommandGetter commandGetter, ParserUtils parserUtils) {
		syntaxTrees = new LinkedHashMap<>();
		commandHistory = new ArrayList<>();
		scopedStorage = storage;
		this.commandGetter = commandGetter;
		this.turtleManager = turtleManager;
		this.viewController = viewController;
		undoIndex = 0;
		this.parserUtils = parserUtils;
	}

	/**
	 * Construct and save the syntax tree for a command, returning true if
	 * successfully constructed false otherwise
	 * 
	 * @param command
	 *            the raw command string to be validated
	 * @return true if valid and syntax tree was made, else false
	 * @throws SLogoException
	 */
	public boolean validateCommand(String command) throws SLogoException {
		if (command == null) {
			return false;
		}
		// Avoid repeated computation for just differing whitespace
		// Need to remove comments
		String commandWithoutComments = parserUtils.stripComments(command);
		String formattedCommand = commandWithoutComments
				.replaceAll(ParserUtils.DELIMITER_REGEX, ParserUtils.STANDARD_DELIMITER).trim();
		syntaxTrees.put(formattedCommand, constructSyntaxTree(
				new PeekingIterator<>(Arrays.asList(formattedCommand.split(ParserUtils.DELIMITER_REGEX)).iterator())));
		return true;
	}

	/**
	 * Execute a command string, usually one that has been previously validated and
	 * parsed into a tree. If tree is not found, tree is built and command verified
	 * prior to execution. Exception thrown if command found to be invalid
	 * 
	 * @param command
	 *            the raw command string to be executed
	 * @throws SLogoException
	 *             if the command is invalid
	 */
	public void executeCommand(String command) throws SLogoException {
		String commandWithoutComments = parserUtils.stripComments(command);
		String formattedCommand = commandWithoutComments
				.replaceAll(ParserUtils.DELIMITER_REGEX, ParserUtils.STANDARD_DELIMITER).trim();
		if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
			syntaxTrees.put(formattedCommand, constructSyntaxTree(new PeekingIterator<String>(
					Arrays.asList(formattedCommand.split(ParserUtils.DELIMITER_REGEX)).iterator())));
			undoIndex = syntaxTrees.size();
		}
		SyntaxNode tree = syntaxTrees.get(formattedCommand);
		tree.execute();
		commandHistory.add(tree);
		undoIndex = commandHistory.size();
	}

	/**
	 * 
	 * @return true if undo can currently be executed, false otherwise
	 */
	public boolean canUndo() {
		return undoIndex > 0 && commandHistory.size() > 0;
	}

	/**
	 * 
	 * @return the set of command strings (formatted) entered for this session
	 */
	public Set<String> getSessionCommands() {
		return syntaxTrees.keySet();
	}

	void clearHistory() {
		syntaxTrees.clear();
		commandHistory.clear();
		undoIndex = 0;
	}

	// need to do a CLEAR before calling
	void undo() throws SLogoException {
		if (canUndo()) {
			undoIndex--;
			for (int i = 0; i < undoIndex; i++) {
				commandHistory.get(i).execute();
			}
		}
	}

	boolean canRedo() {
		return undoIndex < commandHistory.size() && commandHistory.size() > 0;
	}

	// need to do a CLEAR before calling
	void redo() throws SLogoException {
		if (canRedo()) {
			undoIndex++;
			for (int i = 0; i < undoIndex; i++) {
				commandHistory.get(i).execute();
			}
		}
	}

	Map<String, SyntaxNode> getSyntaxTrees() {
		return syntaxTrees;
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
		return rootNode;
	}

	// Parsing command which chooses appropriate parsing command for
	// construction of next node
	SyntaxNode makeExpTree(PeekingIterator<String> it) throws SLogoException {
		if (!it.hasNext()) { // Done parsing
			throw new UnbalancedArgumentsException();
		}
		String nextToken = it.peek();
		if (nextToken.length() == 0 || nextToken.matches(ParserUtils.DELIMITER_REGEX)) {
			it.next();
			return makeExpTree(it);
		}
		if (nextToken.equals(ParserUtils.VARIABLE_ARGS_START_DELIMITER)) {
			it.next();
			// MAKE is a special case, the only non-ValueNode to support multiple args
			if (!parserUtils.isValueNode(commandGetter.getCommandNodeClass(it.peek()))) {
				return makeVariableDefinitionNodeForMultipleParameters(it);
			}
			return makeExpTreeForVariableParameters(it);
		}
		if (parserUtils.isNumeric(nextToken)) {
			it.next();
			return new ConstantNode(Double.parseDouble(nextToken));
		}
		// Need to check for user-declared methods here
		if (scopedStorage.existsFunction(nextToken)) {
			return makeFunctionNode(it);
		}
		if (scopedStorage.existsVariable(nextToken) || nextToken.matches(ParserUtils.VARIABLE_REGEX)) {
			it.next();
			return new VariableNode(nextToken, scopedStorage, nextToken);
		}
		// Dispatch appropriate method
		try {
			Method nextParsingMethod = commandGetter.getParsingMethod(nextToken);
			return (SyntaxNode) nextParsingMethod.invoke(this, it);
		} catch (IllegalAccessException | InvocationTargetException badCommand) {
			throw determineExceptionCause(nextToken, badCommand);
		}
	}

	private ValueNode makeValueNode(PeekingIterator<String> it) throws SLogoException {
		if (it == null || !it.hasNext()) {
			throw new IllegalArgumentException();
		}
		String commandName = it.next();
		// Use reflection to invoke right command constructor
		ValueNode valueNode = getValueNodeFromCommandName(commandName);
		int numChildren = valueNode.getDefaultNumberOfArguments();
		SyntaxNode nextChild;
		for (int child = 0; child < numChildren; child++) {
			nextChild = makeExpTree(it);
			valueNode.addChild(nextChild);
		}
		return valueNode;
	}

	private VariableDefinitionNode makeVariableDefinitionNodeForMultipleParameters(PeekingIterator<String> it)
			throws SLogoException {
		// Consume the MAKE / SET token
		String token = it.next();
		// Extract the variable names
		List<String> varNames = new ArrayList<>();
		List<SyntaxNode> varExps = new ArrayList<>();
		addVariablesAndExpressionsToLists(it, varNames, varExps);
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(ParserUtils.VARIABLE_ARGS_END_DELIMITER);
		}
		// Consume the ')' token
		it.next();
		return new VariableDefinitionNode(token, scopedStorage, varNames.toArray(new String[0]),
				varExps.toArray(new SyntaxNode[0]));
	}

	private void addVariablesAndExpressionsToLists(PeekingIterator<String> it, List<String> varNames,
			List<SyntaxNode> varExps) throws SLogoException {
		while (it.hasNext() && !it.peek().equals(ParserUtils.VARIABLE_ARGS_END_DELIMITER)) {
			String nextVarName = it.next();
			varNames.add(nextVarName);
			// Assert proper regex
			if (!nextVarName.matches(ParserUtils.VARIABLE_REGEX)) {
				throw new IllegalSyntaxException(nextVarName);
			}
			// Catch unmatched params
			if (!it.hasNext()) {
				throw new UnbalancedMakeException();
			}
			SyntaxNode nextVarExp = makeExpTree(it);
			varExps.add(nextVarExp);
		}
	}

	// Only ValueNodes can have variable params ? - EDIT : NO, 'MAKE' also
	private SyntaxNode makeExpTreeForVariableParameters(PeekingIterator<String> it) throws SLogoException {
		if (it == null || !it.hasNext()) {
			throw new IllegalArgumentException();
		}
		// Retrieve just the root node corresponding to this command
		// it advanced by one token
		String commandName = it.peek();
		ValueNode root = makeValueNode(it);
		if (root == null || !root.canTakeVariableNumberOfArguments()) {
			throw new VariableArgumentsException(commandName);
		}
		SyntaxNode nextChild;
		while (it.hasNext() && !it.peek().equals(ParserUtils.VARIABLE_ARGS_END_DELIMITER)) {
			nextChild = makeExpTree(it);
			root.addChild(nextChild);
		}
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(ParserUtils.VARIABLE_ARGS_END_DELIMITER);
		}
		// Consume ')' token
		it.next();
		return root;
	}

	// Called when expecting a list
	RootNode getCommandsListRoot(PeekingIterator<String> it) throws SLogoException {
		if (it == null || !it.hasNext()) {
			throw new IllegalArgumentException();
		}
		String listStartToken = it.next();
		if (!listStartToken.equals(ParserUtils.LIST_START_DELIMITER)) {
			throw new IllegalSyntaxException(listStartToken);
		}
		RootNode commandsListRoot = new RootNode();
		while (it.hasNext() && !it.peek().equals(ParserUtils.LIST_END_DELIMITER)) {
			commandsListRoot.addChild(makeExpTree(it));
		}
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(ParserUtils.LIST_END_DELIMITER);
		}
		// Consume ']' token
		it.next();
		return commandsListRoot;
	}

	private SLogoException determineExceptionCause(String nextToken, ReflectiveOperationException badCommand) {
		Throwable cause = badCommand.getCause();
		if (cause instanceof SLogoException) {
			return (SLogoException) cause;
		} else {
			return new UndefinedCommandException(nextToken);
		}
	}

	private FunctionNode makeFunctionNode(PeekingIterator<String> it) throws SLogoException {
		// Consume the function name
		String funcName = it.next();
		// Need to know number of arguments taken by function
		int numberOfFunctionParameters = scopedStorage.getNumberOfFunctionParameters(funcName);
		List<SyntaxNode> functionParameters = new ArrayList<>();
		for (int parameterIndex = 0; parameterIndex < numberOfFunctionParameters; parameterIndex++) {
			functionParameters.add(makeExpTree(it));
		}
		functionParameters.forEach(e -> System.out.print(e.serialize() + " "));
		return new FunctionNode(funcName, scopedStorage, funcName, functionParameters);
	}

	private ValueNode getValueNodeFromCommandName(String commandName) throws SLogoException {
		try {
			Class commandClass = commandGetter.getCommandNodeClass(commandName);
			Constructor constructor;
			Object[] constructorArgs;
			if (parserUtils.isTurtleNode(commandClass)) {
				constructor = commandClass.getConstructor(String.class, TurtleController.class);
				constructorArgs = new Object[] { commandName, turtleManager };
			} else if (parserUtils.isViewNode(commandClass)) {
				constructor = commandClass.getConstructor(String.class, ViewController.class);
				constructorArgs = new Object[] { commandName, viewController };
			} else {
				constructor = commandClass.getConstructor(String.class);
				constructorArgs = new Object[] { commandName };
			}
			return (ValueNode) constructor.newInstance(constructorArgs);
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
				| InstantiationException e) {
			throw determineExceptionCause(commandName, e);
		}
	}

	private VariableDefinitionNode makeVariableDefinitionNode(PeekingIterator<String> it) throws SLogoException {
		// Consume the MAKE / SET token
		String token = it.next();
		String varName = it.next();
		SyntaxNode varExp = makeExpTree(it);
		return new VariableDefinitionNode(token, scopedStorage, new String[] { varName }, new SyntaxNode[] { varExp });
	}

	private RepeatNode makeRepeatNode(PeekingIterator<String> it) throws SLogoException {
		// Consume the REPEAT token
		String token = it.next();
		SyntaxNode numberOfTimesToRepeat = makeExpTree(it);
		RootNode commandsListRoot = getCommandsListRoot(it);
		return new RepeatNode(token, scopedStorage, numberOfTimesToRepeat, commandsListRoot);
	}

	private DoTimesNode makeDoTimesNode(PeekingIterator<String> it) throws SLogoException {
		// Consume the DOTIMES token
		String token = it.next();
		// Consume the '[' token
		String listStartToken = it.next();
		if (!listStartToken.equals(ParserUtils.LIST_START_DELIMITER)) {
			throw new IllegalSyntaxException(ParserUtils.LIST_START_DELIMITER);
		}
		String varName = it.next();
		SyntaxNode limitExp = makeExpTree(it);
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(ParserUtils.LIST_END_DELIMITER);
		}
		// Consume ']' token
		it.next();
		RootNode commandsListRoot = getCommandsListRoot(it);
		// Error will be resolved when limit arg type is changed to SyntaxNode in
		// DoTimesNode constructor
		return new DoTimesNode(token, scopedStorage, varName, limitExp, commandsListRoot);
	}

	private LoopNode makeForLoopNode(PeekingIterator<String> it) throws SLogoException {
		// Consume the FOR token
		String token = it.next();
		// Consume the '[' token
		String listStartToken = it.next();
		if (!listStartToken.equals(ParserUtils.LIST_START_DELIMITER)) {
			throw new IllegalSyntaxException(ParserUtils.LIST_START_DELIMITER);
		}
		String varName = it.next();
		SyntaxNode startExp = makeExpTree(it);
		SyntaxNode endExp = makeExpTree(it);
		SyntaxNode incrExp = makeExpTree(it);
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(ParserUtils.LIST_END_DELIMITER);
		}
		// Consume ']' token
		it.next();
		RootNode commandsListRoot = getCommandsListRoot(it);
		return new LoopNode(token, scopedStorage, varName, startExp, endExp, incrExp, commandsListRoot);
	}

	private IfNode makeIfNode(PeekingIterator<String> it) throws SLogoException {
		// Consume the IF token
		String token = it.next();
		SyntaxNode conditionExpression = makeExpTree(it);
		RootNode commandsListRoot = getCommandsListRoot(it);
		return new IfNode(token, scopedStorage, conditionExpression, commandsListRoot);
	}

	private IfElseNode makeIfElseNode(PeekingIterator<String> it) throws SLogoException {
		// Consume the IfELSE token
		String token = it.next();
		SyntaxNode conditionExpression = makeExpTree(it);
		RootNode trueCommandsListRoot = getCommandsListRoot(it);
		RootNode falseCommandsListRoot = getCommandsListRoot(it);
		return new IfElseNode(token, scopedStorage, conditionExpression, trueCommandsListRoot, falseCommandsListRoot);
	}

	// TODO - SPLIT INTO SMALLER HELPERS
	private FunctionDefinitionNode makeFunctionDefinitionNode(PeekingIterator<String> it) throws SLogoException {
		// Consume the MAKEUSERINSTRUCTION token
		String token = it.next();
		String funcName = it.next();
		// To support recursive functions, may need to store the funcName here
		scopedStorage.registerFunctionName(funcName);
		List<String> variableNames = parserUtils.getVariableNamesFromList(it);
		// need to do this here for recursion to work
		scopedStorage.addFunctionParameterNames(funcName, variableNames);
		RootNode funcRoot = getCommandsListRoot(it);
		return new FunctionDefinitionNode(token, scopedStorage, funcName, funcRoot);
	}

	private TellNode makeTellNode(PeekingIterator<String> it) throws SLogoException {
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
		// Consume the ASKWITH button
		String token = it.next();
		RootNode queriesRoot = getCommandsListRoot(it);
		RootNode commandsListRoot = getCommandsListRoot(it);
		AskWithNode askWithNode = new AskWithNode(token, turtleManager, queriesRoot, commandsListRoot);
		return askWithNode;
	}

}
