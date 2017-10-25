package backend.turtle;

import backend.error_handling.SLogoException;

public class IsPenDownNode extends TurtleNode {

	public IsPenDownNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().isCurrentTurtlePenDown();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
