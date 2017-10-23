package backend.math_nodes;

import backend.TwoArgNode;

/**
 * @author Ben Schwennesen
 */
public class PowerNode extends TwoArgNode {

    @Override
    public double executeSelf(double... operands) { return Math.pow(operands[0], operands[1]); }
    
}
