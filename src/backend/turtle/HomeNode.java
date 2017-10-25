package backend.turtle;

import backend.error_handling.SLogoException;

public class HomeNode extends TurtleNode {

	public HomeNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().goHomeCurrentTurtle();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}
	
}
