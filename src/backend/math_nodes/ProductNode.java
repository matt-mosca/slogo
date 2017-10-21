package backend.math_nodes;

import backend.ValueNode;

public class ProductNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) {
        double product = 1.0;
        for (double operand : operands) {
            product *= operand;
        }
        return product;
    }

}
