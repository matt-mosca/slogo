package backend.turtle;

import backend.error_handling.SLogoException;

public class XCoordinateNode extends TurtleNode {

	public XCoordinateNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().currentTurtlesXCor();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
