package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class LeftNode extends TurtleNode {

	public LeftNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		for (double angle : arguments) {
			getTurtleController().rotateCurrentTurtles(false, angle);			
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
