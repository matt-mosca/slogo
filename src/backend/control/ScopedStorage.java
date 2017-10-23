package backend.control;

import backend.SyntaxNode;
import backend.error_handling.UndefinedFunctionException;
import backend.error_handling.UndefinedVariableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    private Stack<String> scopeStack = new Stack<>();

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
        return value;
    }

    void addFunctionParameterNames(String functionName, List<String> parameterNames) {
        functionParameterNames.put(functionName, parameterNames);
    }

    void enterScope(String newScope) {
        Set<String> scopes = scopeMap.getOrDefault(newScope, new HashSet<>());
        scopes.add(newScope);
        scopes.addAll(scopeMap.getOrDefault(currentScope, new HashSet<>()));
        scopeMap.put(newScope, scopes);
        scopeStack.push(currentScope);
        currentScope = newScope;
    }

    // for loops, conditionals
    void enterAnonymousScope() {
        enterScope(String.valueOf(anonymousId++));
    }

    void exitScope() {
        String lastScope = (scopeStack.isEmpty() ? GLOBAL : scopeStack.pop());
        Set<String> outerScopes = scopeMap.getOrDefault(lastScope, new HashSet<>());
        outerScopes.removeAll(scopeMap.getOrDefault(currentScope, new HashSet<>()));
        currentScope = lastScope;
    }
    /* GETTERS */

    public boolean existsVariable(String name) { return functionVariables.get(currentScope).containsKey(name); }
    public boolean existsFunction(String name) { return functionRoots.containsKey(name); }

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

    double getVariableValue(String variableName) throws UndefinedVariableException {
        if (!existsVariable(variableName)) {
            throw new UndefinedVariableException(variableName);
        }
        return functionVariables.get(currentScope).get(variableName);
    }
}
