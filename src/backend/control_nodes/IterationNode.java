package backend.control_nodes;

import backend.ScopedStorage;
import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class IterationNode extends DataAccessingNode {

    private String iterationVariable;
    private final double END;
    private final double INCREMENT;
    private SyntaxNode subtree;

    public IterationNode(ScopedStorage store, String iterationVariable,
                         double end, double increment, SyntaxNode subtree) {
        super(store);
        this.iterationVariable = iterationVariable;
        this.END = end;
        this.INCREMENT = increment;
        this.subtree = subtree;
    }

    public double execute() throws SLogoException {
        double result = 0.0;
        double start = getStore().getVariableValue(iterationVariable);
        for (double i = start; i < END; i += INCREMENT) {
            getStore().setVariable(iterationVariable, i);
            result = subtree.execute();
        }
        return result;
    }
}
