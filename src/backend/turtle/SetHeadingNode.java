package backend.turtle;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

public class SetHeadingNode extends TurtleNode {

	SyntaxNode angleExpr;
	
	public SetHeadingNode(TurtleFactory turtleFactory, SyntaxNode angleExpr) {
		super(turtleFactory);
		this.angleExpr = angleExpr;
	}

	@Override
	public double execute() throws SLogoException {
		return getTurtleFactory().setCurrentTurtleHeading(angleExpr.execute());
	}

}
