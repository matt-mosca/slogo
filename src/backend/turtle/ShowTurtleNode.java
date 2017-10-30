package backend.turtle;

import backend.error_handling.SLogoException;

public class ShowTurtleNode extends TurtleNode {

	public ShowTurtleNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().showCurrentTurtles();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
