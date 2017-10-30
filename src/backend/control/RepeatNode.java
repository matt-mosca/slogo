package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;
import backend.math.ConstantNode;

/**
 * REPEAT expr [ command(s) ] -- runs command(s) given in the list the value of expr number of times returns the value
 *                                of the final command executed (or 0 if no commands are executed)
 *                                note, the value of the current iteration, starting at 1, is automatically assigned to
 *                                the variable :repcount so that it can be accessed by the command(s)
 * @author Ben Schwennesen
 */

public class RepeatNode extends LoopNode {

    private static final String REPEAT_VARIABLE_NAME = ":repcount";
    private static final SyntaxNode START_EXPRESSION = new ConstantNode(1);
    private static final SyntaxNode INCREMENT_EXPRESSION = new ConstantNode(1);

    public RepeatNode(String commandName, ScopedStorage store, SyntaxNode limitExpression, SyntaxNode subtree) throws SLogoException {
        super(commandName, store, REPEAT_VARIABLE_NAME, START_EXPRESSION, limitExpression, INCREMENT_EXPRESSION, subtree);
    }

}
