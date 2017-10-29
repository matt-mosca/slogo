package backend.turtle;

import backend.error_handling.SLogoException;

public class SetHeadingNode extends TurtleNode {

	public SetHeadingNode(TurtleController turtleController) {
		super(turtleController);
	}
	
	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double angle = arguments[0];
		return getTurtleController().setCurrentTurtlesHeading(angle);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}

}
