package backend.error_handling;

/**
 * Exception thrown when the user tries to access an undefined variable. Caught and message is not printed as
 * undefined variables are assumed to be zero, per the project specifications.
 *
 * @author Ben Schwennesen
 */
public class UndefinedVariableException extends SyntaxCausedException {

    /**
     * Construct an undefined variable exception.
     *
     * @param badVariableReferenceToken - token where the user tries to access the undefined command
     */
    public UndefinedVariableException(String badVariableReferenceToken) {
        super(badVariableReferenceToken);
    }
}
