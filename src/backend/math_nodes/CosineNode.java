package backend.math_nodes;

import backend.ValueNode;

public class CosineNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return Math.cos(operands[0]); }

}
