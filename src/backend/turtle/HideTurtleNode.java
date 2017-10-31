package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class HideTurtleNode extends TurtleNode {

	public HideTurtleNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().hideCurrentTurtles();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}
	
}
