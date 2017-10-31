package backend.math;

import backend.VarArgNode;

import java.util.Arrays;

/**
 * Computes the sum of all its operands, of which there can be an arbitrary number.
 *
 * @author Ben Schwennesen
 */
public class SumNode extends VarArgNode {

    public SumNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) {
        return Arrays.stream(operands).sum();
    }
}
