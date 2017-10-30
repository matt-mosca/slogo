package backend.turtle;

import backend.error_handling.SLogoException;

public class XCoordinateNode extends TurtleNode {

	public XCoordinateNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().currentTurtlesXCor();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
