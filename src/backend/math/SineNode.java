package backend.math;

import backend.OneArgNode;

/**
 * Computes the value of the sine function for its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class SineNode extends OneArgNode {

    public SineNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.sin(operands[0]); }

}
