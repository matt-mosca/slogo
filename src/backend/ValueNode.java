package backend;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import commands.AbstractCommand;

public class ValueNode extends SyntaxNode {

	protected ValueNode(AbstractCommand command) throws InvocationTargetException, IllegalAccessException {
		super(command);
	}
	
	
	// Execute in post-order
	@Override
	public double execute() throws IllegalAccessException, InvocationTargetException {
		List<SyntaxNode> children = getChildren();
		double[] arguments = new double[children.size()];
		for (int index = 0; index < arguments.length; index ++) {
			arguments[index] = children.get(index).execute();
		}
		return getCommand().execute();
	}
}
