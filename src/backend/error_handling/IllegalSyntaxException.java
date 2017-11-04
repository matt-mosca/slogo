package backend.error_handling;

/**
 * Exception thrown when a general syntax error occurs.
 *
 * @author Ben Schwennesen
 */
public class IllegalSyntaxException extends SyntaxCausedException {

    /**
     * Generate an illegal syntax exception.
     *
     * @param badSyntaxToken - the token where the syntax error occured
     */
    public IllegalSyntaxException(String badSyntaxToken) { super(badSyntaxToken); }

}
