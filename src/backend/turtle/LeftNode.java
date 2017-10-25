package backend.turtle;

import backend.error_handling.SLogoException;

public class LeftNode extends TurtleNode {

	public LeftNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleFactory().rotateCurrentTurtle(false, angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
