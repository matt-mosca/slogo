package backend.turtle;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

public class LeftNode extends TurtleNode {

	SyntaxNode angleExpr;
	
	public LeftNode(TurtleFactory turtleFactory, SyntaxNode angleExpr) {
		super(turtleFactory);
		this.angleExpr = angleExpr;
	}

	@Override
	public double execute() throws SLogoException {
		return getTurtleFactory().rotateCurrentTurtle(false, angleExpr.execute());
	}

}
