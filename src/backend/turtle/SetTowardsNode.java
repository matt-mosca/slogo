package backend.turtle;

import backend.error_handling.SLogoException;

public class SetTowardsNode extends TurtleNode {

	public SetTowardsNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double x = arguments[0];
		double y = arguments[1];
		return getTurtleController().setTowardsCurrentTurtles(x, y);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 2;
	}

}
