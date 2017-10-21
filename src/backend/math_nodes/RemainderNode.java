package backend.math_nodes;

import backend.ValueNode;

public class RemainderNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] % operands[1]; }

}
