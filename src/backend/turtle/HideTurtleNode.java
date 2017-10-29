package backend.turtle;

import backend.error_handling.SLogoException;

public class HideTurtleNode extends TurtleNode {

	public HideTurtleNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().hideCurrentTurtles();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}
	
}
