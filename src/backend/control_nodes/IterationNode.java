package backend.control_nodes;

import backend.FunctionsStore;
import backend.SyntaxNode;

public class IterationNode extends DataAccessingNode {

    private String iterationVariable;
    private final double END;
    private final double INCREMENT;
    private SyntaxNode subtree;

    public IterationNode(FunctionsStore store, String iterationVariable,
                         double end, double increment, SyntaxNode subtree) {
        super(store);
        this.iterationVariable = iterationVariable;
        this.END = end;
        this.INCREMENT = increment;
        this.subtree = subtree;
    }

    public double execute() {
        double result = 0.0;
        double start = getVariableValue(iterationVariable);
        for (double i = start; i < END; i += INCREMENT) {
            setVariable(iterationVariable, i);
            result = subtree.execute();
        }
        return result;
    }
}
