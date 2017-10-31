package backend.turtle;

import backend.error_handling.SLogoException;

public class LeftNode extends TurtleNode {

	public LeftNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		for (double angle : arguments) {
			getTurtleController().rotateCurrentTurtles(false, angle);			
		}
		return arguments[arguments.length] - 1;
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
