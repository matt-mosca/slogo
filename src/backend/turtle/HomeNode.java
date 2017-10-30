package backend.turtle;

import backend.error_handling.SLogoException;

public class HomeNode extends TurtleNode {

	public HomeNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().goHomeCurrentTurtles();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}
	
}
