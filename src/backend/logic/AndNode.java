package backend.logic;

import backend.VarArgNode;

/**
 * @author Ben Schwennesen
 */
public class AndNode extends VarArgNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] != 0 && operands[1] != 0 ? 1 : 0; }

}