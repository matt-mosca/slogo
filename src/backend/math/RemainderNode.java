package backend.math;

import backend.TwoArgNode;

/**
 * @author Ben Schwennesen
 */
public class RemainderNode extends TwoArgNode {

    public RemainderNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return operands[0] % operands[1]; }
    
}
