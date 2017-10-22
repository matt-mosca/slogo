package backend.math_nodes;

import backend.OneArgNode;

public class SineNode extends OneArgNode {

    @Override
    public double executeSelf(double... operands) { return Math.sin(operands[0]); }

}
