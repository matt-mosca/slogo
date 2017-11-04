package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;
import backend.math.ConstantNode;

/**
 * Syntax node for executing repeat commands.
 *
 * @author Ben Schwennesen
 */

public class RepeatNode extends LoopNode {

    private static final String REPEAT_VARIABLE_NAME = ":repcount";
    private static final SyntaxNode START_EXPRESSION = new ConstantNode(1);
    private static final SyntaxNode INCREMENT_EXPRESSION = new ConstantNode(1);

    /**
     * Construct a syntax node for the do-times command.
     *
     * @param store - the object used to store the current workspace's functions and variables
     * @param limitExpression - the syntax node representing the iteration limit
     * @param subtree - the syntax node to execute at each iteration
     */
    public RepeatNode(ScopedStorage store, SyntaxNode limitExpression, SyntaxNode subtree) throws SLogoException {
        super(store, REPEAT_VARIABLE_NAME, START_EXPRESSION, limitExpression, INCREMENT_EXPRESSION, subtree);
    }

}
