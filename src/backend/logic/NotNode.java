package backend.logic;

import backend.OneArgNode;

/**
 * Syntax node for computing the logical NOT of its (sole) operand, returning one if the operand is zero and zero
 * otherwise.
 *
 * @author Ben Schwennesen
 */
public class NotNode extends OneArgNode {

	@Override
    public double executeSelf(double... operands) { return operands[0] == 0 ? 1 : 0; }
    
}
