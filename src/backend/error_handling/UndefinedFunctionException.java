package backend.error_handling;

import utilities.ExceptionMessageGetter;

/**
 *
 *
 * Name of this class is used in super class to determine the message of the error.
 *
 * @author Ben Schwennesen
 */
public class UndefinedFunctionException extends SyntaxCausedException {

    public UndefinedFunctionException(String badFunctionReferennceToken) {
        super(badFunctionReferennceToken);
    }

}
