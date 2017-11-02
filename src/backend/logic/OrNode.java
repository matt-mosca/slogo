package backend.logic;

import backend.VarArgNode;

/**
 * Computes the logical OR of its operands, returning one if at least one is not zero and zero otherwise.
 *
 * @author Ben Schwennesen
 */
public class OrNode extends VarArgNode {

	@Override
    public double executeSelf(double... operands) {
        for (double operand : operands) {
            if (operand != 0) {
                return 1;
            }
        }
        return 0;
    }
    
}
