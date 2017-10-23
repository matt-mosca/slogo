package backend.turtle;

import backend.SyntaxNode;

public abstract class TurtleNode implements SyntaxNode {
	
	private TurtleFactory turtleFactory;
	
	public TurtleNode(TurtleFactory turtleFactory) {
		this.turtleFactory = turtleFactory;
	}
	
	protected TurtleFactory getTurtleFactory() {
		return turtleFactory;
	}
	
}
