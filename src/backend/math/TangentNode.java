package backend.math;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class TangentNode extends OneArgNode {

    public TangentNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.tan(operands[0]); }
    
}
