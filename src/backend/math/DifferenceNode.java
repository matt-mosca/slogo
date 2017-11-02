package backend.math;

import backend.TwoArgNode;

/**
 * Computer the difference of its first operand and second operand. Variable arguments are not allowed.
 *
 * @author Ben Schwennesen
 */
public class DifferenceNode extends TwoArgNode {

	@Override
    public double executeSelf(double... operands) { return operands[0] - operands[1]; }
    
}
