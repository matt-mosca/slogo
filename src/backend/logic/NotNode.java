package backend.logic;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class NotNode extends OneArgNode {

    public NotNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) { return operands[0] == 0 ? 1 : 0; }
    
}
