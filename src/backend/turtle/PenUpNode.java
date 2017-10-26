package backend.turtle;

import backend.error_handling.SLogoException;

public class PenUpNode extends TurtleNode {

	public PenUpNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().setCurrentTurtlesPenUp();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
