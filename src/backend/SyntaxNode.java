package backend;

import commands.AbstractCommand;
import java.util.ArrayList;
import java.util.List;

public class SyntaxNode {

	private AbstractCommand command;
	private List<SyntaxNode> children;
	private int size;

	SyntaxNode(AbstractCommand command) {
		this.command = command;
		children = new ArrayList<>();
		this.size = 1;
	}

	void addChild(SyntaxNode child) {
		children.add(child);
		size += child.getSize();
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

}
