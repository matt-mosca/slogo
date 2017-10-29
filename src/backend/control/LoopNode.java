package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class LoopNode extends ControlNode {

    private String iterationVariable;
    private SyntaxNode startExpression, endExpression, incrementExpression, subtree;

    public LoopNode(ScopedStorage store, String iterationVariable,
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
        getStore().enterAnonymousScope();
        double result = 0.0;
        double start = startExpression.execute(),
                end = endExpression.execute(),
                increment = incrementExpression.execute();
        for (double i = start; i <= end; i += increment) {
            getStore().setVariable(iterationVariable, i);
            result = subtree.execute();
        }
        getStore().exitScope();
        return result;
    }
    
    // Worth exposing this for the sake of serialization??
    // If so, consider returning a deep-copy instead?
    public SyntaxNode getCommandSubtree() {
    		return subtree;
    }
    
    public String getIterationVariable() {
    		return iterationVariable;
    }
    
    public SyntaxNode getEndExpression() {
    		return endExpression;
    }
    
}
