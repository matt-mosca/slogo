package backend.turtle;

import backend.error_handling.SLogoException;
import backend.error_handling.UnevenNumberOfArgumentsException;

/**
 * @author Adithya Raghunathan
 */
public class SetTowardsNode extends TurtleNode {

	public SetTowardsNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		if (arguments.length % 2 != 0) {
			throw new UnevenNumberOfArgumentsException();
		}
		double result = 0;
		for (int index = 0; index < arguments.length; index += 2) {
			double x = arguments[index];
			double y = arguments[index + 1];
			result = getTurtleController().setTowardsCurrentTurtles(x, y);
		}
		return result;
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 2;
	}
	
	@Override 
	public boolean canTakeVariableNumberOfArguments() {
		return true;
	}

}
