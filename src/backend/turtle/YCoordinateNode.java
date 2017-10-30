package backend.turtle;

import backend.error_handling.SLogoException;

public class YCoordinateNode extends TurtleNode {


	public YCoordinateNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().currentTurtlesYCor();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
