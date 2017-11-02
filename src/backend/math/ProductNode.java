package backend.math;

import backend.VarArgNode;

import java.util.Arrays;

/**
 * Computer the product of all its operands, of which there can be an arbitrary number.
 *
 * @author Ben Schwennesen
 */

public class ProductNode extends VarArgNode {

	@Override
    public double executeSelf(double... operands) {
        return Arrays.stream(operands).reduce(1, (a,b) -> a*b);
    }

}
