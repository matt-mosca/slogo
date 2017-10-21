package backend;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class ValueNode implements SyntaxNode {

	List<SyntaxNode> children;
	
	protected ValueNode() throws InvocationTargetException, IllegalAccessException {
		children = new ArrayList<>();
	}
	
	// Execute in post-order
	@Override
	public double execute() throws IllegalAccessException, InvocationTargetException {
		double[] arguments = new double[children.size()];
		for (int index = 0; index < arguments.length; index ++) {
			arguments[index] = children.get(index).execute();
		}
		return executeSelf(arguments);
	}
	
	public abstract double executeSelf(double ... arguments) throws IllegalAccessException, InvocationTargetException;
	
	public void addChild(SyntaxNode child) {
		children.add(child);
	}
}
