package backend.math;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class LogNode extends OneArgNode {

    public LogNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.log(operands[0]); }
    
}
