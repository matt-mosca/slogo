package backend.math_nodes;

import backend.ValueNode;

public class SumNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) {
        double sum = 0.0;
        for (double operand : operands) {
            sum += operand;
        }
        return sum;
    }

}
