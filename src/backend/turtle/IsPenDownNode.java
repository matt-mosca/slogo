package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class IsPenDownNode extends TurtleNode {

	public IsPenDownNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().isCurrentTurtlesPenDown();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
