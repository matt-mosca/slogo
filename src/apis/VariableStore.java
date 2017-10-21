package apis;

import java.util.List;
import java.util.Map.Entry;

/**
 * Stores the variables declared by the user in the frontend using a HashMap.
 */
public interface VariableStore {

    /**
     * Get the currently stored variables.
     *
     * @return a list of the valid variables the user has stored up to this point
     */
    List<Entry> getVariables();

    /**
     * Add a new validated variable.
     *
     * @param variableMapEntry - the new variable to add to the map of all currently available variables
     */
    void saveVariable(Entry variableMapEntry);

    /**
     * Check if a variable exists in the current environment.
     *
     * @param variable - the name of the variable referenced by the user in the command area
     * @return true if the variable is a key in the variables map
     */
    boolean existsVariable(String variable);

    /**
     * Get the value of a variable referenced by the user.
     *
     * @param variable - the name of the referenced variable
     * @return the value of the variable in the variable map
     * @throws IllegalArgumentException - thrown if the variable is not in the variable map
     */
    double getVariableValue(String variable) throws IllegalArgumentException;
}

