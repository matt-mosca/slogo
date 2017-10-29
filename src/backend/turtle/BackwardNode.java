package backend.turtle;

import backend.error_handling.SLogoException;

public class BackwardNode extends TurtleNode {

	public BackwardNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		double pixels = arguments[0];
		return getTurtleController().moveCurrentTurtlesForward(-pixels);
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}
	
	
}
