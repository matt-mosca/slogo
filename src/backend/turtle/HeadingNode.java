package backend.turtle;

import backend.error_handling.SLogoException;

public class HeadingNode extends TurtleNode {

	public HeadingNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().currentTurtleHeading();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}