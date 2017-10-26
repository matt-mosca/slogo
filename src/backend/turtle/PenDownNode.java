package backend.turtle;

import backend.error_handling.SLogoException;

public class PenDownNode extends TurtleNode {

	public PenDownNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
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
