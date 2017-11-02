package backend.turtle;

import backend.error_handling.SLogoException;

/**
 * @author Adithya Raghunathan
 */
public class YCoordinateNode extends TurtleNode {


	public YCoordinateNode(TurtleController turtleController) {
		super(turtleController);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return getTurtleController().currentTurtlesYCor();
	}

	@Override
	public int getDefaultNumberOfArguments() {
		return 0;
	}

}
