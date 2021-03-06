package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class XCoordinateNode extends TurtleNode {

	public XCoordinateNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().currentTurtlesXCor();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
