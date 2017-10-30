package backend.logic;

import backend.VarArgNode;

import java.util.Arrays;

/**
 * @author Ben Schwennesen
 */
public class EqualNode extends VarArgNode {

    @Override
    public double executeSelf(double... operands) {
        return Arrays.stream(operands).distinct().limit(2).count() <= 1 ? 1 : 0;
    }

}