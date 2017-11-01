package backend.math;

import backend.TwoArgNode;

/**
 * Computer the quotient of its first operand divided by its second operand.
 *
 * @author Ben Schwennesen
 */
public class QuotientNode extends TwoArgNode {

	@Override
    public double executeSelf(double... operands) { return operands[0] / operands[1]; }

}
