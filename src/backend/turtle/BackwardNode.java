package backend.turtle;

import backend.error_handling.SLogoException;

public class BackwardNode extends TurtleNode {

	public BackwardNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double pixels = arguments[0];
		return getTurtleFactory().moveCurrentTurtlesForward(-pixels);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}
	
	
}
