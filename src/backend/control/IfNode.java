package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class IfNode extends ControlNode {

    private SyntaxNode conditionExpression;

    private SyntaxNode trueBranch;

    public IfNode(String commandName, ScopedStorage store, SyntaxNode conditionExpression, SyntaxNode trueBranch) {
        super(commandName, store);
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

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
}
