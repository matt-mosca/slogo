package backend.math_nodes;

import backend.ValueNode;

public class ArctangentNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return Math.atan(operands[0]); }

}
