package backend.turtle;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

public class BackwardNode extends TurtleNode {

	SyntaxNode expr;
	
	public BackwardNode(TurtleFactory turtleFactory, SyntaxNode expr) {
		super(turtleFactory);
		this.expr = expr;
	}

	@Override
	public double execute() throws SLogoException {
		double pixels = expr.execute();
		return getTurtleFactory().moveCurrentTurtleForward(-pixels);
	}
	
	
}
