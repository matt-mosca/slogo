package backend.math;

import backend.SyntaxNode;

/**
 * Syntax node for returning the value of the constant pi.
 *
 * @author Ben Schwennesen
 */
public class PiNode implements SyntaxNode {
	
    @Override
    public double execute() {
        return Math.PI;
    }

	@Override
	public boolean canTakeVariableNumberOfArguments() {
		return false;
	}

}
