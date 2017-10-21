package backend.control_nodes;

import backend.SyntaxNode;
import sun.awt.SunHints;

public class IfNode implements SyntaxNode  {

    private SyntaxNode conditionExpression;

    private SyntaxNode trueBranch;

    public IfNode(SyntaxNode conditionExpression, SyntaxNode trueBranch) {
        this.conditionExpression = conditionExpression;
    }

    protected boolean isTrue() { return conditionExpression.execute() == 1; }

    protected double executeTrueBranch() { return trueBranch.execute(); }

    @Override
    public double execute() {
        if (isTrue()) {
            return executeTrueBranch();
        }
        return 0;
    }
}
