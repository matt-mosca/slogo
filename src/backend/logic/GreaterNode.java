package backend.logic;

import backend.TwoArgNode;

/**
 * Determines whether its first operand is greater than its second operand, returning one if so and zero otherwise.
 *
 * @author Ben Schwennesen
 */
public class GreaterNode extends TwoArgNode {

	@Override
    public double executeSelf(double... operands) { return operands[0] > operands[1] ? 1 : 0; }

}
