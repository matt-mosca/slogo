package backend.turtle;

import backend.error_handling.SLogoException;

public class IsShowingNode extends TurtleNode {

	public IsShowingNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString, turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().isCurrentTurtlesShowing();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
