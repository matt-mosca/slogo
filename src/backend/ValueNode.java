package backend;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import commands.AbstractCommand;

public class ValueNode extends SyntaxNode {

	protected ValueNode(AbstractCommand command) throws InvocationTargetException, IllegalAccessException {
		super(command);
	}

	@Override
	public double parseSyntaxTree() throws InvocationTargetException, IllegalAccessException {
		SyntaxNode tree = this;
		double[] operands = new double[tree.getChildren().size()];
		List<SyntaxNode> children = tree.getChildren();
		for (int childIndex = 0; childIndex < children.size(); childIndex++) {
			SyntaxNode child = children.get(childIndex);
			operands[childIndex] = child.parseSyntaxTree();
		}
		// FOR DEBUGGING, return directly in future without printing
		double result;
		// TODO - make this more elegant (Ben temp fix)
		if (operands.length == 0) {
			result = tree.getCommand().execute(null);
		} else if (operands.length == 1) {
			result = tree.getCommand().execute(operands[0]);
		} else if (operands.length == 2 && !tree.getCommand().takesVariableArguments()) {
			result = tree.getCommand().execute(operands[0], operands[1]);
		} else {
			result = tree.getCommand().execute(operands);
		}
		return result;
	}
	
	@Override
	public double execute() throws IllegalAccessException, InvocationTargetException {
		List<SyntaxNode> children = getChildren();
		double[] values = new double[children.size()];
		for (int index = 0; index < values.length; index ++) {
			values[index] = children.get(index).execute();
		}
		return getCommand().execute(values);
	}
}
