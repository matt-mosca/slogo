package backend.error_handling;

/**
 * Exception thrown when the user attempts to use an function they have not defined.
 *
 * @author Ben Schwennesen
 */
public class UndefinedFunctionException extends SyntaxCausedException {

    /**
     * Construct an exception to throw when the user tries to call a function they haven't defined.
     *
     * @param badFunctionReferenceToken - the token where the user tries to call the function
     */
    public UndefinedFunctionException(String badFunctionReferenceToken) {
        super(badFunctionReferenceToken);
    }
}
