package backend.turtle;

import backend.error_handling.SLogoException;

public class HeadingNode extends TurtleNode {

	public HeadingNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().currentTurtlesHeading();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
