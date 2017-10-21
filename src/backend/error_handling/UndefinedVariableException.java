package backend.error_handling;

import utilities.ExceptionMessageGetter;

/**
 *
 *
 * Name of this class is used in super class to determine the message of the error.
 *
 * @author Ben Schwennesen
 */
public class UndefinedVariableException extends SLogoException {

    private final String UNDEFINED_VARIABLE_REFERENCE;

    public UndefinedVariableException(String variableReferenceToken) {
        UNDEFINED_VARIABLE_REFERENCE = variableReferenceToken;
    }

    @Override
    void setMessageUsingInstanceName() {
        setMessage(ExceptionMessageGetter.getMessage(this.getClass().getName()) + UNDEFINED_VARIABLE_REFERENCE);
    }
}
