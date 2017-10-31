package backend.logic;

import backend.VarArgNode;

import java.util.Arrays;

/**
 * Determines the equality of its operands, returning one if all are equal and zero otherwise.
 *
 * @author Ben Schwennesen
 */
public class EqualNode extends VarArgNode {

    public EqualNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) {
        return Arrays.stream(operands).distinct().limit(2).count() <= 1 ? 1 : 0;
    }

}