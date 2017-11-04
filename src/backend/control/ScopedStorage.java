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

/**
 * @author Ben Schwennesen
 */
public class ScopedStorage extends Observable {

	private Map<String, Map<String, Double>> functionVariables = new HashMap<>();
	private Map<String, List<String>> functionParameterNames = new HashMap<>();
	private Map<String, SyntaxNode> functionRoots = new HashMap<>();

	// use Deque because java.util.Stack has no interface and extends vector
	private Deque<String> scopeStack = new ArrayDeque<>();

	private static final String GLOBAL = "global";
	private final int STORAGE_SUCCESS = 1;

	// track anonymous scopes (loops, conditionals)
	private int anonymousId = 0;

	public ScopedStorage() {
		functionVariables.put(GLOBAL, new HashMap<>());
		scopeStack.push(GLOBAL);
	}

	double addFunction(String functionName, SyntaxNode functionRoot) {
		functionRoots.put(functionName, functionRoot);
		return STORAGE_SUCCESS;
	}

	public double setVariable(String name, double value) {
		String scopeToDefineIn = getScopeOfDefinition(name);
		return setVariableInScope(scopeToDefineIn, name, value);
	}

	double setVariableInScope(String scope, String variableName, double value) {
		Map<String, Double> functionVariableMap = functionVariables.getOrDefault(scope, new HashMap<>());
		functionVariableMap.put(variableName, value);
		functionVariables.put(scope, functionVariableMap);
		setChanged();
		notifyObservers();
		return value;
	}

	/**
	 *
	 * @return scope where the variable is already defined, or current scope if it's not defined yet
	 */
	private String getScopeOfDefinition(String variableName) {
		Iterator<String> innerToOuterScope = scopeStack.descendingIterator();
		while (innerToOuterScope.hasNext()) {
			String scope = innerToOuterScope.next();
			if (functionVariables.getOrDefault(scope, new HashMap<>()).containsKey(variableName)) {
				return scope;
			}
		}
		// if not yet defined, variable should be defined in current scope where it's being set or used
		return scopeStack.peekLast();
	}

	public void addFunctionParameterNames(String functionName, List<String> parameterNames) {
		functionParameterNames.put(functionName, parameterNames);
		// point at which frontend should display the available "user-defined command" (function)
		setChanged();
		notifyObservers();
	}

	void enterScope(String newScope) {
		scopeStack.addLast(newScope);
	}

	// for loops, conditionals
	void enterAnonymousScope() {
		enterScope(String.valueOf(anonymousId++));
	}

	void exitScope() {
		scopeStack.pollLast();
		setChanged();
		notifyObservers();
	}
	/* GETTERS */

	// Whether variable is accessible from current scope, inclusive of outer ones
	public boolean existsVariable(String name) {
		return getAllAvailableVariables().containsKey(name);
	}

	public boolean existsFunction(String name) {
		return functionParameterNames.containsKey(name);
	}

	// used by the parser
	public int getNumberOfFunctionParameters(String functionName) throws UndefinedFunctionException {
		if (!functionParameterNames.containsKey(functionName)) {
			throw new UndefinedFunctionException(functionName);
		}
		return functionParameterNames.get(functionName).size();
	}

	// used to temporarily note that a function has been declared, for recursion
	public void registerFunctionName(String functionName) {
		addFunctionParameterNames(functionName, new ArrayList<>());
	}

	public Map<String, Double> getAllAvailableVariables() {
		Map<String, Double> availableVariables = new TreeMap<>();
		availableVariables.putAll(functionVariables.get(GLOBAL));
		Iterator<String> innerToOuterScope = scopeStack.descendingIterator();
		while (innerToOuterScope.hasNext()) {
			availableVariables.putAll(functionVariables.getOrDefault(innerToOuterScope.next(), new HashMap<>()));
		}
		return availableVariables;
	}

	// return function name and parameter name list
	public Map<String, List<String>> getDefinedFunctions() {
		return functionParameterNames;
	}

	List<String> getFunctionParameterNames(String functionName) {
		return functionParameterNames.get(functionName);
	}

	SyntaxNode getFunctionRoot(String functionName) throws UndefinedFunctionException {
		if (!existsFunction(functionName)) {
			throw new UndefinedFunctionException(functionName);
		} else {
			return functionRoots.get(functionName);
		}
	}

	// Need to check all scopes from innermost to outermost ... not just innermost
	double getVariableValue(String variableName) throws UndefinedVariableException {
		if (!existsVariable(variableName)) {
			throw new UndefinedVariableException(variableName);
		}
		// Search from top to bottom of stack
		Iterator<String> innerToOuterScope = scopeStack.descendingIterator();
		while (innerToOuterScope.hasNext()) {
			String scope = innerToOuterScope.next();
			if (functionVariables.getOrDefault(scope, new HashMap<>()).containsKey(variableName)) {
				return functionVariables.get(scope).get(variableName);
			}
		}
		return 0;
	}

	// for undo / redo
	public void clear() {
		functionVariables.clear();
		functionParameterNames.clear();
		functionVariables.put(GLOBAL, new HashMap<>());
		functionRoots.clear();
		scopeStack.clear();
	}
}
