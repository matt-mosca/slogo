package backend.turtle;

import backend.error_handling.SLogoException;

public class ShowTurtleNode extends TurtleNode {

	public ShowTurtleNode(String commandString, TurtleController turtleController) {
		super(commandString, turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().showCurrentTurtles();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
