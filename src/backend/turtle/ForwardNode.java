package backend.turtle;

import backend.error_handling.SLogoException;

public class ForwardNode extends TurtleNode {

	public ForwardNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}


	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double pixels = arguments[0];
		return getTurtleController().moveCurrentTurtlesForward(pixels);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
