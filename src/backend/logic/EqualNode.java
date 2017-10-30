package backend.logic;

import backend.VarArgNode;

import java.util.Arrays;

/**
 * @author Ben Schwennesen
 */
public class EqualNode extends VarArgNode {

    public EqualNode(String commandString) {
		super(commandString);
	}

	@Override
    public double executeSelf(double... operands) {
        System.out.println(Arrays.stream(operands).distinct().limit(2).count() <= 1 ? 1 : 0);
        return Arrays.stream(operands).distinct().limit(2).count() <= 1 ? 1 : 0;
    }

}