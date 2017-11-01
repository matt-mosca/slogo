package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class IfElseNode extends IfNode {

    private SyntaxNode falseBranch;

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
    
    public SyntaxNode getFalseBranch() {
    		return falseBranch;
    }
    
}
