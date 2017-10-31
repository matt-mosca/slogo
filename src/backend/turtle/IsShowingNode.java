package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class IsShowingNode extends TurtleNode {

	public IsShowingNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().isCurrentTurtlesShowing();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
