package backend.math;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class ArctangentNode extends OneArgNode {

    public ArctangentNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.atan(operands[0]); }
    
}
