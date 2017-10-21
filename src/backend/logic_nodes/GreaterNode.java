package backend.logic_nodes;

import backend.ValueNode;

public class GreaterNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] > operands[1] ? 1 : 0; }

}
