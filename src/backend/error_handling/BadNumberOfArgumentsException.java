package backend.error_handling;

import utilities.ExceptionMessageGetter;

/**
 * @author Ben Schwennesen
 */
public class BadNumberOfArgumentsException extends SLogoException {

    private final String FUNCTION_NAME;
    private final int EXPECTED_NUMBER_OF_ARGUMENTS;

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
