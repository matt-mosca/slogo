package backend.control;

import backend.SyntaxNode;
import backend.error_handling.UndefinedFunctionException;
import backend.error_handling.UndefinedVariableException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

/**
 * @author Ben Schwennesen
 */
public class ScopedStorage {

	Map<String, Map<String, Double>> functionVariables = new HashMap<>();
	Map<String, List<String>> functionParameterNames = new HashMap<>();
	Map<String, SyntaxNode> functionRoots = new HashMap<>();
	Map<String, Set<String>> scopeMap = new HashMap<>();

	private Deque<String> scopeStack = new ArrayDeque<>();

	private String currentScope;

	private static final String GLOBAL = "global";

	private final int STORAGE_SUCCESS = 1;

	// track anonymous scopes (loops, conditionals)
	private int anonymousId = 0;

	public ScopedStorage() {
		functionVariables.put(GLOBAL, new HashMap<>());
		currentScope = GLOBAL;
		Set<String> accessibleFromGlobal = new HashSet<>();
		accessibleFromGlobal.add(GLOBAL);
		scopeMap.put(GLOBAL, accessibleFromGlobal);
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
		functionVariables.put(currentScope, functionVariableMap);
		return value;
	}

	void addFunctionParameterNames(String functionName, List<String> parameterNames) {
		functionParameterNames.put(functionName, parameterNames);
	}

	void enterScope(String newScope) {
		Set<String> scopes = scopeMap.getOrDefault(newScope, new HashSet<String>());
		scopes.add(GLOBAL);
		scopes.add(newScope);
		scopes.addAll(scopeMap.getOrDefault(currentScope, new HashSet<>()));
		scopeMap.put(newScope, scopes);
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
		return functionRoots.containsKey(name);
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
		Set<String> currentFunctionScopes = scopeMap.getOrDefault(currentScope, new HashSet<>());
		for (String currentFunctionScopeMember : currentFunctionScopes) {
			availableVariables.putAll(functionVariables.getOrDefault(currentFunctionScopeMember, new HashMap<>()));
		}
		return availableVariables;
	}

	List<String> getCurrentFunctionParameterNames() {
		return functionParameterNames.getOrDefault(currentScope, new ArrayList<>());
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

	private boolean variableExistsInCurrentScope(String variableName) {
		if (!functionVariables.containsKey(currentScope)) { // No variables set in this scope yet
			return false;
		}
		return functionVariables.get(currentScope).containsKey(variableName);
	}
}
