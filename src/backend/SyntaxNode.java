package backend;

import commands.AbstractCommand;
import java.util.ArrayList;
import java.util.List;

public class SyntaxNode {

	private AbstractCommand command;
	private List<SyntaxNode> children;
	private int size;
	// TODO - get rid of this if possible
	private boolean hasVariableArgs;

	SyntaxNode(AbstractCommand command) {
		this.command = command;
		children = new ArrayList<>();
		this.size = 1;
	}

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

	public double execute() { return 0.0; }

}
