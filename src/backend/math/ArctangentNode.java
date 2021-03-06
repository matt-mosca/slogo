package backend.math;

import backend.OneArgNode;

/**
 * Syntax node for computing the value of the arctangent function for its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class ArctangentNode extends OneArgNode {

	@Override
    public double executeSelf(double... operands) { return Math.atan(operands[0]); }
    
}
