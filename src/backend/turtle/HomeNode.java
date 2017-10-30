package backend.turtle;

import backend.error_handling.SLogoException;

public class HomeNode extends TurtleNode {

	public HomeNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().goHomeCurrentTurtles();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}
	
}
