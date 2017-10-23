package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class IfNode implements SyntaxNode  {

    private SyntaxNode conditionExpression;

    private SyntaxNode trueBranch;

    public IfNode(SyntaxNode conditionExpression, SyntaxNode trueBranch) {
        this.conditionExpression = conditionExpression;
    }

    protected boolean isTrue() throws SLogoException{ return conditionExpression.execute() == 1; }

    protected double executeTrueBranch() throws SLogoException { return trueBranch.execute(); }

    @Override
    public double execute() throws SLogoException{
        if (isTrue()) {
            return executeTrueBranch();
        }
        return 0;
    }
}
