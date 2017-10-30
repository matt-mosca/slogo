package backend.control;

import backend.Parser;
import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class LoopNode extends ControlNode {

    private String iterationVariable;
    private SyntaxNode startExpression, endExpression, incrementExpression, subtree;

    public LoopNode(String commandName, ScopedStorage store, String iterationVariable,
                    SyntaxNode startExpression, SyntaxNode endExpression,
                    SyntaxNode incrementExpression, SyntaxNode subtree) {
        super(commandName, store);
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
        //addBoundsToSerialization(iterationVariable, start, end, increment);
        for (double i = start; i <= end; i += increment) {
            getStore().setVariable(iterationVariable, i);
            result = subtree.execute();
            
        }
        getStore().exitScope();
        return result;
    }
    
    /*
    private void addBoundsToSerialization(String iterationVariable, double start, double end, double increment) {
		String commandName = getCommandName();
		String serializedBounds = commandName + Parser.STANDARD_DELIMITER + Parser.LIST_START_DELIMITER + Parser.STANDARD_DELIMITER
				+ iterationVariable + Parser.STANDARD_DELIMITER + Double.toString(start)
				+ Parser.STANDARD_DELIMITER + Double.toString(end) + Parser.STANDARD_DELIMITER
				+ Double.toString(increment) + Parser.STANDARD_DELIMITER + Parser.LIST_END_DELIMITER;
    }
    */
    
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

    // Deprecated in favor of Debugger's custom execute method
	@Override
	public String serialize() {
		// Dont serialize the subtree
		return null;
	}
    
    public SyntaxNode getLoopedCommand() {
		return subtree;
    }

}
