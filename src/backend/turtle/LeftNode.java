package backend.turtle;

import backend.error_handling.SLogoException;

public class LeftNode extends TurtleNode {

	public LeftNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleFactory().rotateCurrentTurtles(false, angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
