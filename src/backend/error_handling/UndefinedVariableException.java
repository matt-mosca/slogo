package backend.error_handling;

/**
 * @author Ben Schwennesen
 */
public class UndefinedVariableException extends SyntaxCausedException {

    public UndefinedVariableException(String badVariableReferenceToken) {
        super(badVariableReferenceToken);
    }
}
