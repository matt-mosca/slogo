package backend.math;

import backend.NoArgNode;

public class ConstantNode extends NoArgNode {
	
	double value;
	
	public ConstantNode(double value) {
		super();
		this.value = value;
	}
	
	@Override
	public double executeSelf(double... arguments) {
		return value;
	}

}
