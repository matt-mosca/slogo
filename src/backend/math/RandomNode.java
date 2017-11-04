package backend.math;

import backend.OneArgNode;

/**
 * Syntax node for computing a random double bounded by its first operand.
 *
 * @author Ben Schwennesen
 */

public class RandomNode extends OneArgNode {

	@Override
    public double executeSelf(double... operands) { return operands[0] * Math.random(); }
    
}
