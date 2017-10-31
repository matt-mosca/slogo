package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class SetHeadingNode extends TurtleNode {

	public SetHeadingNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}
	
	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleController().setCurrentTurtlesHeading(angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
