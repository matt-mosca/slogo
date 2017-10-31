package backend.math;

import backend.OneArgNode;

/**
 * Computes the natural logarithm of its (sole) operand.
 *
 * @author Ben Schwennesen
 */
public class LogNode extends OneArgNode {

    public LogNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.log(operands[0]); }
    
}
