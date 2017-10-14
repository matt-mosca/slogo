package apis;

/**
 * Validates and parses the commands the user enters in the frontend, building a syntax tree from the parsed commands.
 */
public interface Parser {

    /**
     * Ensures that an entered command is valid in the SLogo language.
     *
     * @param commandString - the raw command as entered by the user in the frontend
     * @return true if the command is valid and false otherwise
     */
    boolean validate(String commandString);

    /**
     * Builds an abstract syntax tree from a validated command, to be traversed in post-order since SLogo commands are
     * in prefix order.
     *
     * @param commandString - the raw, validated command entered by the user
     * @throws IllegalArgumentException - thrown if an unvalidated command is passed
     */
    void parseCommand(String commandString) throws IllegalArgumentException;
}

