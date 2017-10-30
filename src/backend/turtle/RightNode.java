package backend.turtle;

import backend.error_handling.SLogoException;

public class RightNode extends TurtleNode {

	public RightNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleFactory().rotateCurrentTurtles(true, angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
