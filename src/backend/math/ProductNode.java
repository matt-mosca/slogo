package backend.math;

import backend.VarArgNode;

import java.util.Arrays;

/**
 * @author Ben Schwennesen
 */
public class ProductNode extends VarArgNode {

    public ProductNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) {
        return Arrays.stream(operands).reduce(1, (a,b) -> a*b);
    }

}
