package backend.math_nodes;

import backend.ValueNode;

public class RandomNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] * Math.random(); }

}
