package backend.turtle;

import backend.error_handling.SLogoException;

public class HideTurtleNode extends TurtleNode {

	public HideTurtleNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().hideCurrentTurtles();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}
	
}
