package backend.turtle;

import backend.error_handling.SLogoException;

public class IDNode extends TurtleNode {

	public IDNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().getActiveTurtleId();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
