package backend.turtle;

import backend.error_handling.SLogoException;

public class TurtlesNode extends TurtleNode {

	public TurtlesNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().getNumberTurtlesCreated();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
