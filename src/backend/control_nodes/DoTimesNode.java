package backend.control_nodes;

import backend.ScopedStorage;
import backend.SyntaxNode;

/**
 * DOTIMES [ variable limit ] [ command(s) ] -- runs command(s) for each value specified in the range, i.e., from
 *                                              (1 - limit) inclusive returns the value of the final command executed
 *                                              (or 0 if no commands are executed) note, variable is assigned to each
 *                                              succeeding value so that it can be accessed by the command(s)
 *
 * @author Ben Schwennesen
 */
public class DoTimesNode extends IterationNode {

    private static final double INCREMENT_BY = 1;

    public DoTimesNode(ScopedStorage store, String iterationVariable, double limit, SyntaxNode subtree) {
        super(store, iterationVariable, limit, INCREMENT_BY, subtree);
    }

}
