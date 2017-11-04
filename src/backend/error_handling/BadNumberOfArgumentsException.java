package backend.error_handling;

import utilities.ExceptionMessageGetter;

/**
 * Exception thrown if a command is called with the wrong number of arguments.
 *
 * @author Ben Schwennesen
 */
public class BadNumberOfArgumentsException extends SLogoException {

    private final String FUNCTION_NAME;
    private final int EXPECTED_NUMBER_OF_ARGUMENTS;

    /**
     * Generate an exception when a command is called with the wrong number of arguments
     *
     * @param badFunctionToken - command as called by the user
     * @param expectedNumberOfArguments - the number of arguments the command requires
     */
    public BadNumberOfArgumentsException(String badFunctionToken, int expectedNumberOfArguments) {
        FUNCTION_NAME = badFunctionToken;
        EXPECTED_NUMBER_OF_ARGUMENTS = expectedNumberOfArguments;
    }

    @Override
    public String getMessage() {
        String className = this.getClass().getName();
        return String.format(ExceptionMessageGetter.getMessage(className),
                EXPECTED_NUMBER_OF_ARGUMENTS, FUNCTION_NAME);
    }
}
