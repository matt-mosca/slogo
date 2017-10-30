package backend.logic;

import backend.TwoArgNode;

/**
 * @author Ben Schwennesen
 */
public class LessNode extends TwoArgNode {

    public LessNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return operands[0] < operands[1] ? 1 : 0; }
    
}
