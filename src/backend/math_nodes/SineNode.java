package backend.math_nodes;

import backend.ValueNode;

public class SineNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return Math.sin(operands[0]); }

}
