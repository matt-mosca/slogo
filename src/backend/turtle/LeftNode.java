package backend.turtle;

import backend.error_handling.SLogoException;

public class LeftNode extends TurtleNode {

	public LeftNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleController().rotateCurrentTurtles(false, angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
