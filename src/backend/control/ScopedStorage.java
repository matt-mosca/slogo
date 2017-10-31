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

	private Deque<String> scopeStack = new ArrayDeque<>();

	private String currentScope;
	private static final String GLOBAL = "global";

	private final int STORAGE_SUCCESS = 1;

	// track anonymous scopes (loops, conditionals)
	private int anonymousId = 0;

	public ScopedStorage() {
		functionVariables.put(GLOBAL, new HashMap<>());
		currentScope = GLOBAL;
	}

	double addFunction(String functionName, SyntaxNode functionRoot) {
		functionRoots.put(functionName, functionRoot);
		return STORAGE_SUCCESS;
	}

	/**
	 *
	 *
	 * @param name
	 * @param value
	 * @return
	 */
	double setVariable(String name, double value) {
		Map<String, Double> functionVariableMap = functionVariables.getOrDefault(currentScope, new HashMap<>());
		functionVariableMap.put(name, value);
		functionVariables.put(getScopeOfDefinition(name), functionVariableMap);
		// point at which frontend should update available variables
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
		return currentScope;
	}

	public void addFunctionParameterNames(String functionName, List<String> parameterNames) {
		functionParameterNames.put(functionName, parameterNames);
		// point at which frontend should display the available "user-defined command" (function)
		setChanged();
		notifyObservers();
	}

	void enterScope(String newScope) {
		scopeStack.addLast(currentScope);
		currentScope = newScope;
	}

	// for loops, conditionals
	void enterAnonymousScope() {
		enterScope(String.valueOf(anonymousId++));
	}

	void exitScope() {
		String lastScope = (scopeStack.isEmpty() ? GLOBAL : scopeStack.pollLast());
		currentScope = lastScope;
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

	List<String> getCurrentFunctionParameterNames() {
		return functionParameterNames.get(currentScope);
	}

	SyntaxNode getCurrentFunctionRoot() throws UndefinedFunctionException {
		if (!existsFunction(currentScope)) {
			throw new UndefinedFunctionException(currentScope);
		} else {
			return functionRoots.get(currentScope);
		}
	}

	// Need to check all scopes from innermost to outermost ... not just
	// innermost
	double getVariableValue(String variableName) throws UndefinedVariableException {
		if (variableExistsInCurrentScope(variableName)) {
			return functionVariables.get(currentScope).get(variableName);
		}
		if (!existsVariable(variableName)) {
			throw new UndefinedVariableException(variableName);
		}
		// Search from top to bottom of stack
		Iterator<String> innerToOuterScope = scopeStack.descendingIterator();
		while (innerToOuterScope.hasNext()) {
			String scope = innerToOuterScope.next();
			if (functionVariables.get(scope).containsKey(variableName)) {
				return functionVariables.get(scope).get(variableName);
			}
		}
		return 0;
	}
	
	// For SAVING TO / LOADING FROM WORKSPACE
	Map<String, Map<String, Double>> getFunctionInfo() {
		return functionVariables;
	}
	
	List<String> getFunctionParameters(String functionName) {
		return functionParameterNames.get(functionName);
	}
	
	private boolean variableExistsInCurrentScope(String variableName) {
		return functionVariables.containsKey(currentScope) &&
				functionVariables.get(currentScope).containsKey(variableName);
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
