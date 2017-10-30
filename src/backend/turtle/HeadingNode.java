package backend.turtle;

import backend.error_handling.SLogoException;

public class HeadingNode extends TurtleNode {

	public HeadingNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().currentTurtlesHeading();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
