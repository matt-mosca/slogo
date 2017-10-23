package backend.math;

import backend.TwoArgNode;

/**
 * @author Ben Schwennesen
 */
public class QuotientNode extends TwoArgNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] / operands[1]; }

}
