package backend;

import backend.error_handling.SLogoException;

import java.util.ArrayList;
import java.util.List;

public abstract class ValueNode implements SyntaxNode {

	List<SyntaxNode> children;
	
	protected ValueNode() {
		children = new ArrayList<>();
	}
	
	// Execute in post-order
	@Override
	public double execute() throws SLogoException {
		double[] arguments = new double[children.size()];
		for (int index = 0; index < arguments.length; index ++) {
			arguments[index] = children.get(index).execute();
		}
		return executeSelf(arguments);
	}
	
	public abstract double executeSelf(double ... arguments) throws SLogoException;
	
	public abstract int getDefaultNumberOfArguments();
	
	public abstract boolean canTakeVariableNumberOfArguments();
	
	public void addChild(SyntaxNode child) {
		children.add(child);
	}
	
	protected List<SyntaxNode> getChildren() {
		return children;
	}
}
