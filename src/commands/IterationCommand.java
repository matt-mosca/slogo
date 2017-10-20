package commands;

import backend.SyntaxNode;

import java.util.Map.Entry;

public class IterationCommand extends NewCommand {

    private Entry<String, Double> iterationVariable;
    private final double END;
    private final double INCREMENT;
    private SyntaxNode subtree;

    public IterationCommand(Entry<String, Double> iterationVariable, double end, double increment, SyntaxNode subtree) {
        this.iterationVariable = iterationVariable;
        this.END = end;
        this.INCREMENT = increment;
        this.subtree = subtree;
    }

    double execute() {
        double result = 0.0;
        for (double i = iterationVariable.getValue(); i < END; i += INCREMENT) {
            iterationVariable.setValue(i);
            result = subtree.execute();
        }
        return result;
    }
}
