package backend.math;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class SineNode extends OneArgNode {

    public SineNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.sin(operands[0]); }

}
