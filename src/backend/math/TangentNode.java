package backend.math;

import backend.OneArgNode;

/**
 * Computes the value of the tangent function for its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class TangentNode extends OneArgNode {

    public TangentNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.tan(operands[0]); }
    
}
