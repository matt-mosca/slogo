package backend.math;

import backend.OneArgNode;

/**
 * Syntax node for computing the value of the sine function for its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class SineNode extends OneArgNode {

	@Override
    public double executeSelf(double... operands) { return Math.sin(operands[0]); }

}
