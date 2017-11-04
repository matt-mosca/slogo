package backend.math;

import backend.OneArgNode;

/**
 * Syntax node for computing the negation of its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class MinusNode extends OneArgNode {

	@Override
    public double executeSelf(double... operands) { return -operands[0]; }
}


