package backend.math_nodes;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class TangentNode extends OneArgNode {

    @Override
    public double executeSelf(double... operands) { return Math.tan(operands[0]); }
    
}
