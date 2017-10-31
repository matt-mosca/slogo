package backend.math;

import backend.OneArgNode;

/**
 * Computes the value of the cosine function for its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class CosineNode extends OneArgNode {

    public CosineNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.cos(operands[0]); }
    
}
