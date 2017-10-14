package apis;

/**
 * The part of the display that shows previously entered commands (including variable-setting commmands).
 */
public interface CommandDisplay {

    /**
     * Add a line to the pane representing a validated command (non variable-setting).
     *
     * @param command - the command as entered by the user
     */
    void addCommand(String command);

    /**
     * Add a line to the pane representing a validated variable-setting command.
     *
     * @param variableName - the name of the variable
     * @param value - the variable's value as a string
     */
    void addVariable(String variableName, String value);
}


