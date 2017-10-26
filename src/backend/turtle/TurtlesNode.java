package backend.turtle;

import backend.error_handling.SLogoException;

public class TurtlesNode extends TurtleNode {

	public TurtlesNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
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
