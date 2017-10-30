package backend.turtle;

import backend.error_handling.SLogoException;

public class PenDownNode extends TurtleNode {

	public PenDownNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().setCurrentTurtlesPenDown();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		// TODO Auto-generated method stub
		return 0;
	}

}
