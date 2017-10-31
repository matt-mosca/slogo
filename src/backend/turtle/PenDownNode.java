package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class PenDownNode extends TurtleNode {

	public PenDownNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().setCurrentTurtlesPenDown();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		// TODO Auto-generated method stub
		return 0;
	}

}
