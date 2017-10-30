package backend.math;

import backend.TwoArgNode;

/**
 * @author Ben Schwennesen
 */
public class DifferenceNode extends TwoArgNode {

    protected DifferenceNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return operands[0] - operands[1]; }
    
}
