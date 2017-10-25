package backend.turtle;

import backend.error_handling.SLogoException;

public class SetHeadingNode extends TurtleNode {

	public SetHeadingNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}
	
	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleFactory().setCurrentTurtleHeading(angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
