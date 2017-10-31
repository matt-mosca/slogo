package backend.turtle;

import backend.error_handling.SLogoException;

public class RightNode extends TurtleNode {

	public RightNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		for (double angle : arguments) {
			getTurtleController().rotateCurrentTurtles(true, angle);			
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
