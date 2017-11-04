package backend.error_handling;

/**
 * Exception thrown when the user tries to supply a variable number of arguments to a command which only accepts a
 * specific number of arguments.
 *
 * @author Ben Schwennesen
 */
public class VariableArgumentsException extends SyntaxCausedException {

    /**
     * Construct an illegal variable-arguments usage exception.
     *
     * @param commandToken - the command reference that does not take variable arguments
     */
    public VariableArgumentsException(String commandToken) {
        super(commandToken);
    }
}

