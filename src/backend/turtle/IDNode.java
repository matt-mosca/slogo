package backend.turtle;

import backend.error_handling.SLogoException;

public class IDNode extends TurtleNode {

	public IDNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().getActiveTurtleId();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
