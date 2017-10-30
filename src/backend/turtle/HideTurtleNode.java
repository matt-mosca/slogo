package backend.turtle;

import backend.error_handling.SLogoException;

public class HideTurtleNode extends TurtleNode {

	public HideTurtleNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().hideCurrentTurtles();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}
	
}
