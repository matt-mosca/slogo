package backend.logic;

import backend.VarArgNode;

import java.util.Arrays;

/**
 * @author Ben Schwennesen
 */
public class NotEqualNode extends VarArgNode {

    public NotEqualNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) {
        return Arrays.stream(operands).distinct().limit(2).count() <= 1 ? 0 : 1;
    }

}
