package backend.math_nodes;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class ArctangentNode extends OneArgNode {

    @Override
    public double executeSelf(double... operands) { return Math.atan(operands[0]); }
    
}
