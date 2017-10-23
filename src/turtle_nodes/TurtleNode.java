package turtle_nodes;

import backend.SyntaxNode;

public abstract class TurtleNode implements SyntaxNode {
	
	// Class being created
	private TurtleManager turtleFactory;
	
	public TurtleNode(TurtleManager turtleFactory) {
		this.turtleFactory = turtleFactory;
	}
	
}
