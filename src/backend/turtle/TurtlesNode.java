package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class TurtlesNode extends TurtleNode {

	public TurtlesNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().getNumberTurtlesCreated();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
