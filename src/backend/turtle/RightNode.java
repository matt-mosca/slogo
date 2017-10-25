package backend.turtle;

import backend.error_handling.SLogoException;

public class RightNode extends TurtleNode {

	public RightNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleFactory().rotateCurrentTurtle(true, angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
