package backend.control;

import backend.SyntaxNode;
import backend.error_handling.UndefinedFunctionException;
import backend.error_handling.UndefinedVariableException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Stores variables and functions defined by the user in specific scopes. Notifies listeners--namely the view, which
 * displays these variables and functions--of changes when they occur.
 *
 * This class is well defined for a number of reasons. For one thing, the functions it contains are all well-scoped and
 * readable, with each having a clearly defined purpose. Furthermore, access is limited as strictly as possible.
 * Syntax nodes which require access to the storage, notably control nodes, are included in the same package so as to
 * expose the methods that allow for the modification of stored elements to as few parts of the system as possible.
 *
 * When the class does need to expose public methods, it does so exposing as little implementation detail as possible.
 * For example, in order to pass details about currently defined functions and variables to the view, it returns deep
 * copies of the maps (ensuring the model data won't be changed by the view) used to store them here; this is flexible
 * as the way variables and functions are stored could still be changed, so long as they were still collected into
 * convenient maps when passed along to the view.
 *
 * Additionally, the class throws meaningful, custom-defined exceptions when the user attempts to access variables
 * and functions that are undefined. This makes it very easy for the frontend to display useful messages to the user
 * about what went wrong with the commands they entered.
 *
 * Finally, the class demonstrates solid understanding of the Observer design pattern. In order to communicate to the
 * view that new functions or variables have been added, the class extends Observable and notifies the view of
 * changes when they occur. This eliminates the need for the our controller class to serve as an intermediary.
 *
 * @author Ben Schwennesen
 */
public class ScopedStorage extends Observable {

	private Map<String, Map<String, Double>> variablesInScope = new HashMap<>();
	private Map<String, List<String>> functionParameterNames = new HashMap<>();
	private Map<String, SyntaxNode> functionRoots = new HashMap<>();

	// use Deque because java.util.Stack has no interface and extends vector
	private Deque<String> scopeStack = new ArrayDeque<>();

	private static final String GLOBAL = "global";
	private final int STORAGE_SUCCESS = 1;

	// track anonymous scopes (loops, conditionals)
	private int anonymousId = 0;

	/**
	 * Construct the function and variable storage object for a workspace.
	 */
	public ScopedStorage() {
		variablesInScope.put(GLOBAL, new HashMap<>());
		scopeStack.push(GLOBAL);
	}

	/**
	 * Enter a new, named scope.
	 *
	 * @param newScope - the scope to enter
	 */
	void enterScope(String newScope) {
		scopeStack.addLast(newScope);
	}

	/**
	 * Enter a part of the program that is unnamed but requires its own scope, such as a loop or conditional.
	 */
	void enterAnonymousScope() {
		enterScope(String.valueOf(anonymousId++));
	}

	/**
	 * Exit the last entered scope. Ensure that the storage object never abandons global scope.
	 */
	void exitScope() {
		if (!scopeStack.isEmpty() && !scopeStack.peekLast().equals(GLOBAL)) {
			scopeStack.pollLast();
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Set a variable in the highest level scope in which it is defined. Highest level means working up from global
	 * out to, for example, anonymous for loop scopes inside of function scopes.
	 *
	 * Access is public since the frontend requires the ability to update individual variables outside of the usual
	 * command-based way of doing this.
	 *
	 * @param name - the name of the variable
	 * @param value - the value to which the variable should be set
	 * @return the newly-set value of the variable
	 */
	public double setVariable(String name, double value) {
		String scopeToDefineIn = determineScopeOfDefinition(name);
		return setVariableInScope(scopeToDefineIn, name, value);
	}

	private String determineScopeOfDefinition(String variableName) {
		Iterator<String> innerToOuterScope = scopeStack.descendingIterator();
		while (innerToOuterScope.hasNext()) {
			String scope = innerToOuterScope.next();
			if (variablesInScope.getOrDefault(scope, new HashMap<>()).containsKey(variableName)) {
				return scope;
			}
		}
		// if not yet defined, variable should be defined in current scope where it's being set or used
		return scopeStack.peekLast();
	}

	/**
	 * Set the value of a variable as defined in a particular scope.
	 *
	 * This is necessary since, for example, a variable could be defined globally, only for a variable of the same
	 * name to be used as the parameter to a function (in which case, the value of the global variable should not be
	 * overwritten).
	 *
	 * @param scope - the scope in which the variable should be defined
	 * @param variableName - the name of the variable
	 * @param value - the value to which the variable should be set
	 * @return the newly-set value of the variable
	 */
	double setVariableInScope(String scope, String variableName, double value) {
		Map<String, Double> functionVariableMap = variablesInScope.getOrDefault(scope, new HashMap<>());
		functionVariableMap.put(variableName, value);
		variablesInScope.put(scope, functionVariableMap);
		setChanged();
		notifyObservers();
		return value;
	}

	/**
	 * Confirm the existence of a variable inside the current scope.
	 *
	 * @param variableName - the name of the variable
	 * @return true if the user has defined this variable previously somewhere accessible to the current scope
	 */
	public boolean existsVariable(String variableName) {
		return getAllAvailableVariables().containsKey(variableName);
	}

	/**
	 * Confirm the existence of a function.
	 *
	 * @param functionName - the name of the function
	 * @return true if the user has defined this function previously
	 */
	public boolean existsFunction(String functionName) {
		return functionParameterNames.containsKey(functionName);
	}

	/**
	 * Register a function and its associated parameters.
	 *
	 * @param functionName - the name of the function
	 * @param parameterNames - the names of the function's parameters
	 */
	public void addFunctionParameterNames(String functionName, List<String> parameterNames) {
		functionParameterNames.put(functionName, parameterNames);
		setChanged();
		notifyObservers();
	}

	/**
	 * Store a function's subtree of execution.
	 *
	 * @param functionName - the name of the function
	 * @param functionRoot - the syntax node to execute whenever the function is called
	 * @return one to indicate successful storage of the function
	 */
	double addFunction(String functionName, SyntaxNode functionRoot) {
		functionRoots.put(functionName, functionRoot);
		return STORAGE_SUCCESS;
	}

	/**
	 * Register the existence of a function prior to parsing its details, so that it may be used recursively.
	 *
	 * @param functionName - the name of the function
	 */
	public void registerFunctionName(String functionName) {
		addFunctionParameterNames(functionName, new ArrayList<>());
	}

	/**
	 * Retrieve the parameters of a user-defined function.
	 *
	 * @param functionName - the name of the function
	 * @return a list of the names of the parameters that were listed when the user defined the function
	 */
	List<String> getFunctionParameterNames(String functionName) {
		return functionParameterNames.get(functionName);
	}

	/**
	 * Retrieve the number of parameters a user-defined function requires.
	 *
	 * @param functionName - the name of the function
	 * @return the number of parameters a call the the function requires
	 * @throws UndefinedFunctionException - in the case that the user has not defined the function
	 */
	public int getNumberOfFunctionParameters(String functionName) throws UndefinedFunctionException {
		if (!existsFunction(functionName)) {
			throw new UndefinedFunctionException(functionName);
		}
		return functionParameterNames.get(functionName).size();
	}

	/**
	 * Retrieve the subtree root associated with a function.
	 *
	 * @param functionName - the name of the function
	 * @return the root of the subtree to be executed when the function is called
	 * @throws UndefinedFunctionException - in the case that the function is undefined
	 */
	SyntaxNode getFunctionRoot(String functionName) throws UndefinedFunctionException {
		if (!existsFunction(functionName)) {
			throw new UndefinedFunctionException(functionName);
		} else {
			return functionRoots.get(functionName);
		}
	}

	/**
	 * Retrieve the value of a variable defined by the user.
	 *
	 * @param variableName - the name of the variable
	 * @return the value of the variable
	 * @throws UndefinedVariableException - in the case that the variable is undefined
	 */
	double getVariableValue(String variableName) throws UndefinedVariableException {
		if (!existsVariable(variableName)) {
			throw new UndefinedVariableException(variableName);
		}
		Iterator<String> innerToOuterScope = scopeStack.descendingIterator();
		while (innerToOuterScope.hasNext()) {
			String scope = innerToOuterScope.next();
			if (variablesInScope.getOrDefault(scope, new HashMap<>()).containsKey(variableName)) {
				return variablesInScope.get(scope).get(variableName);
			}
		}
		return 0;
	}

	/**
	 * Retrieve all the persistent (that is, defined outside of an anonymous scope such as a loop) variables defined
	 * by the user.
	 *
	 * @return a deep copy map from variable names to their values
	 */
	public Map<String, Double> getAllAvailableVariables() {
		Map<String, Double> availableVariables = new TreeMap<>();
		availableVariables.putAll(variablesInScope.get(GLOBAL));
		Iterator<String> innerToOuterScope = scopeStack.descendingIterator();
		while (innerToOuterScope.hasNext()) {
			availableVariables.putAll(variablesInScope.getOrDefault(innerToOuterScope.next(), new HashMap<>()));
		}
		return availableVariables;
	}

	/**
	 * Retrieve all the functions the user has defined in the current workspace.
	 *
	 * @return a deep copy map of the stored function names to lists of their parameter names
	 */
	public Map<String, List<String>> getDefinedFunctions() {
		return functionParameterNames.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> new ArrayList<>(e.getValue())));
	}

	/**
	 * Remove all stored variables and functions, as part of resetting the model.
	 */
	public void clear() {
		variablesInScope.clear();
		functionParameterNames.clear();
		variablesInScope.put(GLOBAL, new HashMap<>());
		functionRoots.clear();
		scopeStack.clear();
	}
}
