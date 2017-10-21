package backend;

import apis.Command;

import java.util.*;
import java.util.Map.Entry;

/**
 * TODO - many commands/nodes need access to the function/var store... how to give them access without making this a
 * TODO - ... static utility class or giving each command/node a copy?
 * TODO - Change (some?) methods to package-private??
 *
 * @author Ben Schwennesen
 */
public class FunctionsStore {

    Map<String, Map<String, Double>> functionVariables = new HashMap<>();
    Map<String, List<Command>> functionCommands = new HashMap<>();

    private Stack<String> scopeStack = new Stack<>();

    private String currentScope;
    public static final String GLOBAL = "global";

    public FunctionsStore() {
        functionVariables.put(GLOBAL, new HashMap<>());
        functionCommands.put(GLOBAL, new ArrayList<>());
        currentScope = GLOBAL;
    }

    public void addCommands(String functionName, Command... commands) {
        List<Command> commandList = functionCommands.getOrDefault(functionName, new ArrayList<>());
        commandList.addAll(Arrays.asList(commands));
        functionCommands.put(functionName, commandList);
    }

    public double setVariable(String name, double value) {
        Map<String, Double> functionVariableMap = functionVariables.getOrDefault(currentScope, new HashMap<>());
        functionVariableMap.put(name, value);
        return value;
    }

    public void enterScope(String newScope) {
        Map<String, Double> functionVariableMap = functionVariables.getOrDefault(newScope, new HashMap<>());
        functionVariableMap.putAll(functionVariables.getOrDefault(currentScope, new HashMap<>()));
        scopeStack.push(currentScope);
        currentScope = newScope;
    }

    public void exitScope() {
        String lastScope = (scopeStack.isEmpty() ? GLOBAL : scopeStack.pop());
        Set<Entry<String, Double>> outerVariables =
                functionVariables.getOrDefault(lastScope, new HashMap<>()).entrySet();
        Iterator<Entry<String, Double>> innerVariablesIterator =
                functionVariables.getOrDefault(currentScope, new HashMap<>()).entrySet().iterator();
        while (innerVariablesIterator.hasNext()) {
            Entry<String, Double> innerVariable = innerVariablesIterator.next();
            if (outerVariables.contains(innerVariable)) {
                innerVariablesIterator.remove();
            }
        }
        currentScope = lastScope;
    }
    /* GETTERS */

    public Set<Entry<String, Double>> getCurrentVariables() {
        return functionVariables.get(currentScope).entrySet();
    }

    // this will be costly but can allow the user to modify a variable without access to this class
    public Entry<String, Double> getVariable(String name) {
        if (!functionVariables.get(currentScope).containsKey(name)) {
            functionVariables.get(currentScope).put(name, 0.0);
        }
        Iterator<Entry<String, Double>> entryIterator = functionVariables.get(currentScope).entrySet().iterator();
        while(entryIterator.hasNext()) {
            Entry<String, Double> entry = entryIterator.next();
            if (entry.getKey().equals(name)) {
                return entry;
            }
        }
        // this will never happen
        return null;
    }

    public Set<String> getDeclaredFunctions() {
        return functionVariables.keySet();
    }

    public List<Command> getFunctionCommands(String functionName)  {
        return functionCommands.getOrDefault(functionName, new ArrayList<>());
    }

    public double getVariableValue(String variableName) {
        return functionVariables.get(currentScope).get(variableName);
    }

    public static void main (String[] args) {
        Map<Integer, Integer> test = new HashMap<>();
        test.put(1,1);
        test.put(2,2);
        for (Entry<Integer, Integer> entry : test.entrySet()) {
            entry.setValue(0);
        }
        System.out.println(test);
    }
}
