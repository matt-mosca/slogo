package backend.math;

import backend.OneArgNode;

/**
 * Computes a random double bounded by its first operand.
 *
 * @author Ben Schwennesen
 */

public class RandomNode extends OneArgNode {

	@Override
    public double executeSelf(double... operands) { return operands[0] * Math.random(); }
    
}
