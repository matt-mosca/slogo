package backend.logic;

import backend.TwoArgNode;

/**
 * @author Ben Schwennesen
 */
public class GreaterNode extends TwoArgNode {

    public GreaterNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return operands[0] > operands[1] ? 1 : 0; }

}
