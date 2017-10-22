package backend.math_nodes;

import backend.TwoArgNode;

public class MinusNode extends TwoArgNode {

    @Override
    public double executeSelf(double... operands) { return -operands[0]; }

}


