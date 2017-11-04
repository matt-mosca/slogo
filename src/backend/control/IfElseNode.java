package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * Syntax node for the 'ifelse' conditional control structure.
 *
 * @author Ben Schwennesen
 */
public class IfElseNode extends IfNode {

    private SyntaxNode falseBranch;

    /**
     * Construct a syntax node for an 'if' control structure.
     *
     * @param store - the object used to store the current workspace's functions and variables
     * @param conditionExpression - the syntax node treated as a boolean, used to control the flow of execution
     * @param trueBranch - the subtree to execute if the condition expression is non-zero
     * @param falseBranch - the subtree to execute if the condition expression is zero
     */
    public IfElseNode(ScopedStorage store, SyntaxNode conditionExpression,
                      SyntaxNode trueBranch, SyntaxNode falseBranch) {
        super(store, conditionExpression, trueBranch);
        this.falseBranch = falseBranch;
    }

    @Override
    public double execute() throws SLogoException {
        double result;
        if (isTrue()) {
            result = executeTrueBranch();
        } else {
            getStore().enterAnonymousScope();
            result = falseBranch.execute();
            getStore().exitScope();
        }
        return result;
    }
}
