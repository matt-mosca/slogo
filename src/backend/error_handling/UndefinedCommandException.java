package backend.error_handling;

/**
 * @author Ben Schwennesen
 */
public class UndefinedCommandException extends SyntaxCausedException {

    public UndefinedCommandException(String commandToken) {
        super(commandToken);
    }
}
