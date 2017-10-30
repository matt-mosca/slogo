package backend.control;

import backend.SyntaxNode;
import backend.math.ConstantNode;

/**
 * DOTIMES [ variable limit ] [ command(s) ] -- runs command(s) for each value specified in the range, i.e., from
 *                                              (1 - limit) inclusive returns the value of the final command executed
 *                                              (or 0 if no commands are executed) note, variable is assigned to each
 *                                              succeeding value so that it can be accessed by the command(s)
 *
 * @author Ben Schwennesen
 */
public class DoTimesNode extends LoopNode {

    private static final SyntaxNode START_EXPRESSION = new ConstantNode(1);
    private static final SyntaxNode INCREMENT_EXPRESSION = new ConstantNode(1);

    public DoTimesNode(String commandName, ScopedStorage store, String iterationVariable, SyntaxNode limitExpression, SyntaxNode subtree) {
        super(commandName, store, iterationVariable, START_EXPRESSION, limitExpression, INCREMENT_EXPRESSION, subtree);
    }
    
    @Override
    public String serialize() {
    		return null;
    }
    
}
