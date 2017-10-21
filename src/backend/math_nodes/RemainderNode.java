package backend.math_nodes;

import backend.TwoArgNode;

public class RemainderNode extends TwoArgNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] % operands[1]; }
    
}
