package backend.math;

import backend.OneArgNode;

/**
 * Syntax node for computing the natural logarithm of its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class LogNode extends OneArgNode {

	@Override
    public double executeSelf(double... operands) { return Math.log(operands[0]); }
    
}
