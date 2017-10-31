package backend.math;

import backend.TwoArgNode;

/**
 * Computes the remainder of its first operand after division by its second operand.
 *
 * @author Ben Schwennesen
 */
public class RemainderNode extends TwoArgNode {

    public RemainderNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return operands[0] % operands[1]; }
    
}
