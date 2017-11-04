package backend.math;

import backend.NoArgNode;

/**
 * Syntax node for returning a constant entered as a token by the user.
 *
 * @author Ben Schwennesen
 */
public class ConstantNode extends NoArgNode {
	
	private double value;

	/**
	 * Construct a syntax node for representing a constant.
	 *
	 * @param value - the value of the constant
	 */
	public ConstantNode(double value) {
		this.value = value;
	}
	
	@Override
	public double executeSelf(double... arguments) {
		return value;
	}

	/**
	 * @return the value of the constant
	 */
	public double getValue() {
		return value;
	}

}
