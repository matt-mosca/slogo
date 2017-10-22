package backend;

import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedFunctionException;
import backend.error_handling.UndefinedVariableException;
import backend.math_nodes.ConstantNode;
import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

/**
 * @author Ben Schwennesen
 */
public class ScopedStorage {

    Map<String, Map<String, Double>> functionVariables = new HashMap<>();
    Map<String, Set<String>> scopeMap = new HashMap<>();
    Map<String, SyntaxNode> functionRoots = new HashMap<>();

    private Stack<String> scopeStack = new Stack<>();

    private String currentScope;
    public static final String GLOBAL = "global";

    private final int STORAGE_SUCCESS = 1;

    public ScopedStorage() {
        functionVariables.put(GLOBAL, new HashMap<>());
        currentScope = GLOBAL;
    }

    public double addFunction(String functionName, SyntaxNode functionRoot) {
        functionRoots.put(functionName, functionRoot);
        return STORAGE_SUCCESS;
    }

    /**
     *
     *
     * Value is passed as null before tree is executed so that a variable's existence can be confirmed before the
     * command where its value is stored is execute.
     *
     * @param name
     * @param value
     * @return
     */
    public double setVariable(String name, @Nullable Double value) {
        Map<String, Double> functionVariableMap = functionVariables.getOrDefault(currentScope, new HashMap<>());
        functionVariableMap.put(name, value);
        return value;
    }

    public void enterScope(String newScope) {
        Set<String> scopes = scopeMap.getOrDefault(newScope, new HashSet<>());
        scopes.add(newScope);
        scopes.addAll(scopeMap.getOrDefault(currentScope, new HashSet<>()));
        scopeMap.put(newScope, scopes);
        scopeStack.push(currentScope);
        currentScope = newScope;
    }

    public void exitScope() {
        String lastScope = (scopeStack.isEmpty() ? GLOBAL : scopeStack.pop());
        Set<String> outerScopes = scopeMap.getOrDefault(lastScope, new HashSet<>());
        outerScopes.removeAll(scopeMap.getOrDefault(currentScope, new HashSet<>()));
        currentScope = lastScope;
    }
    /* GETTERS */

    public boolean existsVariable(String name) { return functionVariables.get(currentScope).containsKey(name); }
    public boolean existsFunction(String name) { return functionRoots.containsKey(name); }

    public Set<Entry<String, Double>> getCurrentVariables() {
        Set<Entry<String, Double>> availableVariables = new HashSet<>();
        Set<String> currentFunctionScopes = scopeMap.getOrDefault(currentScope, new HashSet<>());
        for (String currentFunctionScopeMember : currentFunctionScopes) {
            availableVariables.addAll(
                    functionVariables
                            .getOrDefault(currentFunctionScopeMember, new HashMap<>())
                            .entrySet());
        }
        return availableVariables;
    }

    public Set<String> getDeclaredFunctions() {
        return functionVariables.keySet();
    }

    public SyntaxNode getFunctionRoot(String functionName) throws UndefinedFunctionException {
        if (!existsFunction(functionName)) {
            throw new UndefinedFunctionException(functionName);
        } else {
            return functionRoots.get(functionName);
        }
    }

    public double getVariableValue(String variableName) throws UndefinedVariableException {
        if (!existsVariable(variableName)) {
            throw new UndefinedVariableException(variableName);
        }
        return functionVariables.get(currentScope).get(variableName);
    }
}
