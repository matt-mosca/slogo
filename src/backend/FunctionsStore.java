package backend;

import apis.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * TODO - Change (some?) methods to package-private??
 * @author Ben Schwennesen
 */
public class FunctionsStore {

    Map<String, Map<String, Double>> functionVariables = new HashMap<>();
    Map<String, List<Command>> functionCommands = new HashMap<>();

    private static final String GLOBAL = "global";

    public FunctionsStore() {
        functionVariables.put(GLOBAL, new HashMap<>());
        functionCommands.put(GLOBAL, new ArrayList<>());
    }

    /*public void addNewFunction(String functionName) {
        functionVariables.put(functionName, new HashMap<>());
        functionCommands.put(functionName, new ArrayList<>());
    }*/

    /*SETTERS*/

    public void addCommands(String functionName, Command... commands) {
        List<Command> commandList = functionCommands.getOrDefault(functionName, new ArrayList<>());
        commandList.addAll(Arrays.asList(commands));
        functionCommands.put(functionName, commandList);
    }

    public void addVariable(String variableName, Double variableValue) {
        addVariable(GLOBAL, variableName, variableValue);
    }

    public void addVariable(String functionName, String variableName, Double variableValue) {
        Map<String, Double> functionVariableMap = functionVariables.getOrDefault(functionName, new HashMap<>());
        functionVariableMap.put(variableName, variableValue);
    }

    /* GETTERS */

    public Set<Map.Entry> getDeclaredVariables() {
        return getDeclaredVariablesInScope(GLOBAL);
    }

    public Set<Map.Entry> getDeclaredVariablesInScope(String functionName) {
        Set<Map.Entry> declaredVariables = new HashSet<>();
        declaredVariables.addAll(functionVariables.get(GLOBAL).entrySet());
        if (functionVariables.containsKey(functionName)) {
            declaredVariables.addAll(functionVariables.get(functionName).entrySet());
        }
        return declaredVariables;
    }

    public Set<String> getDeclaredFunctions() {
        return functionVariables.keySet();
    }

    public List<Command> getFunctionCommands(String functionName)  {
        return functionCommands.getOrDefault(functionName, new ArrayList<>());
    }
}
