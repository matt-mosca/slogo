package backend.turtle;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

public class ForwardNode extends TurtleNode {

	SyntaxNode pixelsExpr;

	public ForwardNode(TurtleFactory turtleFactory, SyntaxNode pixelsExpr) {
		super(turtleFactory);
		this.pixelsExpr = pixelsExpr;
	}

	@Override
	public double execute() throws SLogoException {
		return getTurtleFactory().moveCurrentTurtleForward(pixelsExpr.execute());
	}

}
