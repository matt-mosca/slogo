package backend.error_handling;

/**
 * @author Ben Schwennesen
 */
public class IllegalSyntaxException extends SyntaxCausedException {

    public IllegalSyntaxException(String badSyntaxToken) { super(badSyntaxToken); }

}
