package backend.error_handling;

/**
 * @author Ben Schwennesen
 */
public class VariableArgumentsException extends SyntaxCausedException {

    public VariableArgumentsException(String commandToken) {
        super(commandToken);
    }
}

