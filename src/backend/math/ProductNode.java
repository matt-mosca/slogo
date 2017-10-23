package backend.math_nodes;

import backend.VarArgNode;

/**
 * @author Ben Schwennesen
 */
public class ProductNode extends VarArgNode {

    @Override
    public double executeSelf(double... operands) {
        double product = 1.0;
        for (double operand : operands) {
            product *= operand;
        }
        return product;
    }

}
