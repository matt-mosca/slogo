package backend.math_nodes;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class CosineNode extends OneArgNode {

    @Override
    public double executeSelf(double... operands) { return Math.cos(operands[0]); }
    
}
