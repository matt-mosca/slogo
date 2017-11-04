package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * Base syntax node for looping control structures. Used directly by 'for' commands; extended for other loop commands.
 *
 * @author Ben Schwennesen
 */
public class LoopNode extends ControlNode {

    private String iterationVariable;
    private SyntaxNode startExpression, limitExpression, incrementExpression, subtree;

    /**
     * Construct a syntax node for the do-times command.
     *
     * @param store - the object used to store the current workspace's functions and variables
     * @param iterationVariable - the variable used to access the current iteration number
     * @param limitExpression - the syntax node representing the iteration limit
     * @param subtree - the syntax node to execute at each iteration
     */
    public LoopNode(ScopedStorage store, String iterationVariable,
                    SyntaxNode startExpression, SyntaxNode limitExpression,
                    SyntaxNode incrementExpression, SyntaxNode subtree) {
        super(store);
        this.iterationVariable = iterationVariable;
        this.startExpression = startExpression;
        this.limitExpression = limitExpression;
        this.incrementExpression = incrementExpression;
        this.subtree = subtree;
    }

    @Override
    public double execute() throws SLogoException {
        getStore().enterAnonymousScope();
        double result = 0.0;
        double start = startExpression.execute(),
                end = limitExpression.execute(),
                increment = incrementExpression.execute();
        for (double i = start; i <= end; i += increment) {
            getStore().setVariable(iterationVariable, i);
            result = subtree.execute();
            
        }
        getStore().exitScope();
        return result;
    }
}
