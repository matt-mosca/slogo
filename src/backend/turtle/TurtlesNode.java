package backend.turtle;

import backend.error_handling.SLogoException;

public class TurtlesNode extends TurtleNode {

	public TurtlesNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().getNumberTurtlesCreated();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
