package backend.error_handling;

import utilities.ExceptionMessageGetter;

/**
 *
 *
 * Name of this class is used in super class to determine the message of the error.
 *
 * @author Ben Schwennesen
 */
public class UndefinedFunctionException extends SLogoException {

    private final String UNDEFINED_FUNCTION_REFERENCE;

    public UndefinedFunctionException(String functionRefenceToken) {
        UNDEFINED_FUNCTION_REFERENCE = functionRefenceToken;
    }

    @Override
    void setMessageUsingInstanceName() {
        setMessage(ExceptionMessageGetter.getMessage(this.getClass().getName()) + UNDEFINED_FUNCTION_REFERENCE);
    }
}
