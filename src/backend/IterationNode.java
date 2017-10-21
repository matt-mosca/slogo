package backend;

import java.lang.reflect.InvocationTargetException;

public class IterationNode extends DataAccessingNode {

    private String iterationVariable;
    private final double END;
    private final double INCREMENT;
    private SyntaxNode subtree;

    public IterationNode(FunctionsStore functionsStore, String iterationVariable,
                         double end, double increment, SyntaxNode subtree) {
        super(functionsStore);
        this.iterationVariable = iterationVariable;
        this.END = end;
        this.INCREMENT = increment;
        this.subtree = subtree;
    }

    public double execute() throws IllegalAccessException, InvocationTargetException {
        double result = 0.0;
        double start = getVariableValue(iterationVariable);
        for (double i = start; i < END; i += INCREMENT) {
            setVariable(iterationVariable, i);
            result = subtree.execute();
        }
        return result;
    }
}
