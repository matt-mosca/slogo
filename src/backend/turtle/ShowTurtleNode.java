package backend.turtle;

import backend.error_handling.SLogoException;

public class ShowTurtleNode extends TurtleNode {

	public ShowTurtleNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().showCurrentTurtle();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
