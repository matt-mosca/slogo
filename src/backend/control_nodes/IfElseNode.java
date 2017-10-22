package backend.control_nodes;

import backend.SyntaxNode;

public class IfElseNode extends IfNode {

    private SyntaxNode falseBranch;

    public IfElseNode(SyntaxNode conditionExpression, SyntaxNode trueBranch, SyntaxNode falseBranch) {
        super(conditionExpression, trueBranch);
        this.falseBranch = falseBranch;
    }

    @Override
    public double execute() {
        if (isTrue()) {
            return executeTrueBranch();
        } else {
            return falseBranch.execute();
        }
    }
}
