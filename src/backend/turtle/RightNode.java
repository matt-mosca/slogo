package backend.turtle;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

public class RightNode extends TurtleNode {

	SyntaxNode angleExpr;
	
	public RightNode(TurtleFactory turtleFactory, SyntaxNode angleExpr) {
		super(turtleFactory);
		this.angleExpr = angleExpr;
	}

	@Override
	public double execute() throws SLogoException {
		return getTurtleFactory().rotateCurrentTurtle(true, angleExpr.execute());
	}

}
