package backend;

public class SyntaxNode {

	private Command command;
	private SyntaxNode left;
	private SyntaxNode right;
	private int size;

	public SyntaxNode(Command command) {
		this.command = command;
		this.size = 1;
	}

	public void setLeft(SyntaxNode left) {
		this.left = left;
		size += left.getSize();
	}

	public void setRight(SyntaxNode right) {
		this.right = right;
		size += right.getSize();
	}

	public Command getCommand() {
		return command;
	}

	public SyntaxNode getLeft() {
		return left;
	}

	public SyntaxNode getRight() {
		return right;
	}
	
	public int getSize() {
		return size;
	}

}
