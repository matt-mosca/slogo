package backend;

public class SyntaxNode {

	private Command command;
	private SyntaxNode left;
	private SyntaxNode right;
	private int size;

	SyntaxNode(Command command) {
		this.command = command;
		this.size = 1;
	}

	void setLeft(SyntaxNode left) {
		this.left = left;
		size += left.getSize();
	}

	void setRight(SyntaxNode right) {
		this.right = right;
		size += right.getSize();
	}

	Command getCommand() {
		return command;
	}

	SyntaxNode getLeft() {
		return left;
	}

	SyntaxNode getRight() {
		return right;
	}

	int getSize() {
		return size;
	}

}
