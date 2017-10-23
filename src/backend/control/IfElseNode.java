package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class IfElseNode extends IfNode {

    private SyntaxNode falseBranch;

    public IfElseNode(SyntaxNode conditionExpression, SyntaxNode trueBranch, SyntaxNode falseBranch) {
        super(conditionExpression, trueBranch);
        this.falseBranch = falseBranch;
    }

    @Override
    public double execute() throws SLogoException {
        if (isTrue()) {
            return executeTrueBranch();
        } else {
            return falseBranch.execute();
        }
    }
}
