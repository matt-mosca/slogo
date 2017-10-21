package backend.logic_nodes;

import backend.TwoArgNode;

public class EqualNode extends TwoArgNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] == operands[1] ? 1 : 0; }

}