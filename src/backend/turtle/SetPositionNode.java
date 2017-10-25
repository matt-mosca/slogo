package backend.turtle;

import backend.error_handling.SLogoException;

public class SetPositionNode extends TurtleNode {

	public SetPositionNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double x = arguments[0];
		double y = arguments[1];
		return getTurtleFactory().setCurrentTurtleXY(x, y);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 2;
	}

}