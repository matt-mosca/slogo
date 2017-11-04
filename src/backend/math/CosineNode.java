package backend.math;

import backend.OneArgNode;

/**
 * Syntax node for computing  the value of the cosine function for its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class CosineNode extends OneArgNode {

	@Override
    public double executeSelf(double... operands) { return Math.cos(operands[0]); }
    
}
