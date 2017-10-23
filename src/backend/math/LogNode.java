package backend.math;

import backend.OneArgNode;

/**
 * @author Ben Schwennesen
 */
public class LogNode extends OneArgNode {

    @Override
    public double executeSelf(double... operands) { return Math.log(operands[0]); }
    
}
