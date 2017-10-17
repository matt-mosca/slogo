package backend;

import java.util.ArrayList;
import java.util.List;

public class SyntaxNode {

	private Command command;
	private List<SyntaxNode> children;
	private int size;

	SyntaxNode(Command command) {
		this.command = command;
		children = new ArrayList<>();
		this.size = 1;
	}

	void addChild(SyntaxNode child) {
		children.add(child);
		size += child.getSize();
	}

	Command getCommand() {
		return command;
	}

	List<SyntaxNode> getChildren() {
		return children;
	}

	int getSize() {
		return size;
	}

}
