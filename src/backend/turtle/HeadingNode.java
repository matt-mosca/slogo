package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class HeadingNode extends TurtleNode {

	public HeadingNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().currentTurtlesHeading();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
