package backend.turtle;

import backend.error_handling.SLogoException;

public class XCoordinateNode extends TurtleNode {

	public XCoordinateNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleFactory().currentTurtlesXCor();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
