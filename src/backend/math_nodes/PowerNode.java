package backend.math_nodes;

import backend.ValueNode;

public class PowerNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return Math.pow(operands[0], operands[1]); }

}
