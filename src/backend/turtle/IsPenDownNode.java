package backend.turtle;

import backend.error_handling.SLogoException;

public class IsPenDownNode extends TurtleNode {

	public IsPenDownNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().isCurrentTurtlesPenDown();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
