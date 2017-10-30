package backend.math;

import backend.TwoArgNode;

/**
 * @author Ben Schwennesen
 */
public class PowerNode extends TwoArgNode {

    public PowerNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return Math.pow(operands[0], operands[1]); }
    
}
