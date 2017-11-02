package backend.math;

import backend.TwoArgNode;

/**
 * Computes the result of raising its first operand to the power of its second operand.
 *
 * @author Ben Schwennesen
 */
public class PowerNode extends TwoArgNode {

	@Override
    public double executeSelf(double... operands) { return Math.pow(operands[0], operands[1]); }
    
}
