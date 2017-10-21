package commands;

import backend.ControlNode;
import backend.SyntaxNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map.Entry;

public class IterationCommand extends ControlCommand {

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

    public double execute() throws IllegalAccessException, InvocationTargetException {
        double result = 0.0;
        double start = iterationVariable.getValue();
        for (double i = start; i < END; i += INCREMENT) {
            iterationVariable.setValue(i);
            result = subtree.execute();
        }
        return result;
    }
}
