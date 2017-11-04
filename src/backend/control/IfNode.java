package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * Syntax node for the 'if' conditional control structure.
 *
 * @author Ben Schwennesen
 */
public class IfNode extends ControlNode {

    private SyntaxNode conditionExpression;
    private SyntaxNode trueBranch;

    /**
     * Construct a syntax node for an 'if' control structure.
     *
     * @param store - the object used to store the current workspace's functions and variables
     * @param conditionExpression - the syntax node treated as a boolean, used to control the flow of execution
     * @param trueBranch - the subtree to execute if the condition expression is non-zero
     */
    public IfNode(ScopedStorage store, SyntaxNode conditionExpression, SyntaxNode trueBranch) {
        super(store);
        this.conditionExpression = conditionExpression;
        this.trueBranch = trueBranch;
    }

    protected boolean isTrue() throws SLogoException{ return conditionExpression.execute() == 1; }

    /**
     * Execute the subtree associated with a true condition. Allow if-else nodes access via extension.
     *
     * @return the value of the true branch subtree's final executed command
     * @throws SLogoException - in the case that the true branch contains a bad command
     */
    protected double executeTrueBranch() throws SLogoException {
        getStore().enterAnonymousScope();
        double result = trueBranch.execute();
        getStore().exitScope();
        return result;
    }

    @Override
    public double execute() throws SLogoException {
        double result = 0;
        if (isTrue()) {
            result = executeTrueBranch();
        }
        return result;
    }
}
