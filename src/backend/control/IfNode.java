package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class IfNode extends ControlNode {

    private SyntaxNode conditionExpression;

    private SyntaxNode trueBranch;

    public IfNode(ScopedStorage store, SyntaxNode conditionExpression, SyntaxNode trueBranch) {
        super(store);
        this.conditionExpression = conditionExpression;
        this.trueBranch = trueBranch;
    }

    protected boolean isTrue() throws SLogoException{ return conditionExpression.execute() == 1; }

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
	
	public SyntaxNode getTrueBranch() {
		return trueBranch;
	}
}
