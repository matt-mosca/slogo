package backend.control_nodes;

import backend.FunctionsStore;
import backend.SyntaxNode;

/**
 * REPEAT expr [ command(s) ] -- runs command(s) given in the list the value of expr number of times returns the value
 *                                of the final command executed (or 0 if no commands are executed)
 *                                note, the value of the current iteration, starting at 1, is automatically assigned to
 *                                the variable :repcount so that it can be accessed by the command(s)
 */

public class RepeatNode extends IterationNode {

    private static final String REPEAT_VARIABLE_NAME = ":repcount";
    private static final double INCREMENT_BY = 1;

    public RepeatNode(FunctionsStore store, SyntaxNode expression, SyntaxNode subtree) {
        super(store, REPEAT_VARIABLE_NAME, expression.execute(), INCREMENT_BY, subtree);
    }

}
