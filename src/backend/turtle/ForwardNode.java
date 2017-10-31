package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class ForwardNode extends TurtleNode {

	public ForwardNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}


	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		// Support multiple args
		for (double pixel : arguments) {
			getTurtleController().moveCurrentTurtlesForward(pixel);
		}
		return arguments[arguments.length - 1];
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}
	
	@Override 
	public boolean canTakeVariableNumberOfArguments() {
		return true;
	}

}
