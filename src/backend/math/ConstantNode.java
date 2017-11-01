package backend.math;

import backend.NoArgNode;

/**
 * Returns a constant entered as a token by the user.
 *
 * @author Ben Schwennesen
 */
public class ConstantNode extends NoArgNode {
	
	double value;
	
	public ConstantNode(double value) {
		this.value = value;
	}
	
	@Override
	public double executeSelf(double... arguments) {
		return value;
	}
	
	public double getValue() {
		return value;
	}

}
