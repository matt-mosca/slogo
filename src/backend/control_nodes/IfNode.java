package backend.control_nodes;

import backend.SyntaxNode;
import sun.awt.SunHints;

public class ConditionalNode extends implements SyntaxNode  {

    private SyntaxNode conditionExpression;

    private SyntaxNode trueBranch;

    public ConditionalNode(SyntaxNode conditionExpression, SyntaxNode trueBranch) {
        this.conditionExpression = conditionExpression;
    }

    protected boolean isTrue() { return conditionExpression.execute() == 1; }

    protected double executeTrueBranch() { return trueBranch.execute(); }

    @Override
    public double execute() {
        if (isTrue()) {
            executeTrueBranch();
        }
    }
}
