package backend.math;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class RandomNode extends OneArgNode {

    public RandomNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return operands[0] * Math.random(); }
    
}
