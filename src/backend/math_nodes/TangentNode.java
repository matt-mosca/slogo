package backend.math_nodes;

import backend.ValueNode;

public class TangentNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return Math.tan(operands[0]); }

}
