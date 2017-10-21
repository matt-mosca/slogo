package backend.math_nodes;

import backend.ValueNode;

public class LogNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return Math.log(operands[0]); }

}
