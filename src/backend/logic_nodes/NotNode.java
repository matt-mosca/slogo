package backend.logic_nodes;

import backend.ValueNode;

public class NotNode extends ValueNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] == 0 ? 1 : 0; }

}
