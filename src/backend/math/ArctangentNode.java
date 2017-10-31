package backend.math;

import backend.OneArgNode;

/**
 * Computes the value of the arctangent function for its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class ArctangentNode extends OneArgNode {

    public ArctangentNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.atan(operands[0]); }
    
}
