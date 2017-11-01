package backend.math;

import backend.SyntaxNode;

/**
 * Returns the value of the constant pi.
 *
 * @author Ben Schwennesen
 */
public class PiNode implements SyntaxNode {

	private String commandString;
    // Want to represent the way the user wrote it?
	public PiNode(String commandString) {
		this.commandString = commandString;
	}
	
    @Override
    public double execute() {
        return Math.PI;
    }

	@Override
	public boolean canTakeVariableNumberOfArguments() {
		return false;
	}

}
