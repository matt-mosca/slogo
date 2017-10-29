package backend.turtle;

import backend.error_handling.SLogoException;

public class IsShowingNode extends TurtleNode {

	public IsShowingNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().isCurrentTurtlesShowing();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
