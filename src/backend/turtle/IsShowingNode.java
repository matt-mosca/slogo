package backend.turtle;

import backend.error_handling.SLogoException;

public class IsShowingNode extends TurtleNode {

	public IsShowingNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().isCurrentTurtleShowing();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
