package backend.math;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class MinusNode extends OneArgNode {

    public MinusNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return -operands[0]; }

}


