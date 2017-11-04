package backend.math;

import backend.OneArgNode;

/**
 * Syntax node for computing the value of the tangent function for its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class TangentNode extends OneArgNode {

	@Override
    public double executeSelf(double... operands) { return Math.tan(operands[0]); }
    
}
