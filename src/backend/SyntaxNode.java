package backend;

import commands.AbstractCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public abstract class SyntaxNode {

	private AbstractCommand command;
	private List<SyntaxNode> children;
	private int size;
	// TODO - get rid of this if possible
	private boolean hasVariableArgs;

	protected SyntaxNode(AbstractCommand command)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		this.command = command;
		children = new ArrayList<>();
		this.size = 1;
	}

	// Handled differently based on type of command
	public abstract double parseSyntaxTree() throws InvocationTargetException, IllegalAccessException;

	void addChild(SyntaxNode child) {
		children.add(child);
		size += child.getSize();
	}

	void setHasVariableArgs(boolean value) {
		hasVariableArgs = value;
	}

	AbstractCommand getCommand() {
		return command;
	}

	List<SyntaxNode> getChildren() {
		return children;
	}

	int getSize() {
		return size;
	}

	boolean hasVariableArgs() {
		return hasVariableArgs;
	}
	
	public abstract double execute() throws IllegalAccessException, InvocationTargetException;

	public double execute() { return 0.0; }

}
