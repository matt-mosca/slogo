package backend.math_nodes;

import backend.VarArgNode;

/**
 * @author Ben Schwennesen
 */
public class SumNode extends VarArgNode {

    @Override
    public double executeSelf(double... operands) {
        double sum = 0.0;
        for (double operand : operands) {
            sum += operand;
        }
        return sum;
    }
    
}
