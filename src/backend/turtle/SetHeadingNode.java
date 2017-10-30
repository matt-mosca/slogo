package backend.turtle;

import backend.error_handling.SLogoException;

public class SetHeadingNode extends TurtleNode {

	public SetHeadingNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}
	
	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleFactory().setCurrentTurtlesHeading(angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
