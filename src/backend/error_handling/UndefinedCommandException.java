package backend.error_handling;

/**
 * Exception thrown when the user attempts to use an undefined SLogo command.
 *
 * @author Ben Schwennesen
 */
public class UndefinedCommandException extends SyntaxCausedException {

    /**
     * Construct an exception to throw when the user attempts to use an undefined command.
     *
     * @param commandToken - the undefined command token
     */
    public UndefinedCommandException(String commandToken) {
        super(commandToken);
    }
}
