package backend.math_nodes;

import backend.TwoArgNode;

public class PowerNode extends TwoArgNode {

    @Override
    public double executeSelf(double... operands) { return Math.pow(operands[0], operands[1]); }
    
}
