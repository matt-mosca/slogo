package backend.math_nodes;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class MinusNode extends OneArgNode {

    @Override
    public double executeSelf(double... operands) { return -operands[0]; }

}


