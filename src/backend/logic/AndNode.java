package backend.logic;

import backend.VarArgNode;

/**
 * Computes the logical AND of its operands, returning one if all are non-zero or zero otherwise.
 *
 * @author Ben Schwennesen
 */
public class AndNode extends VarArgNode {

    public AndNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) {
		for (double operand : operands) {
			if (operand == 0) {
				return 0;
			}
		}
		return 1;
    }

}
