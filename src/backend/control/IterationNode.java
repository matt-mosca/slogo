package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class IterationNode extends DataAccessingNode {

    private String iterationVariable;
    private SyntaxNode startExpression, endExpression, incrementExpression, subtree;

    public IterationNode(ScopedStorage store, String iterationVariable,
                         SyntaxNode startExpression, SyntaxNode endExpression,
                         SyntaxNode incrementExpression, SyntaxNode subtree) {
        super(store);
        this.iterationVariable = iterationVariable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.incrementExpression = incrementExpression;
        this.subtree = subtree;
    }

    public double execute() throws SLogoException {
        double result = 0.0;
        double start = startExpression.execute(),
                end = endExpression.execute(),
                increment = incrementExpression.execute();
        for (double i = start; i <= end; i += increment) {
            getStore().setVariable(iterationVariable, i);
            result = subtree.execute();
        }
        return result;
    }
}
