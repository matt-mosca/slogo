package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class PenUpNode extends TurtleNode {

	public PenUpNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().setCurrentTurtlesPenUp();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
