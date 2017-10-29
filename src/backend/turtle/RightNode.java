package backend.turtle;

import backend.error_handling.SLogoException;

public class RightNode extends TurtleNode {

	public RightNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleController().rotateCurrentTurtles(true, angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
