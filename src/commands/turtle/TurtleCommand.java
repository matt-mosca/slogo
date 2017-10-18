package commands.turtle;

import apis.TurtleDisplay;
import commands.AbstractCommand;

public class TurtleCommand extends AbstractCommand {

	private TurtleListener turtleListener = TurtleListener.getInstance();
	
	public TurtleCommand(Class commandType, String methodToInvoke, int numberOfDoubleParameters)
			throws NoSuchMethodException {
		super(commandType, methodToInvoke, numberOfDoubleParameters);
	}

	private double forward(double pixels) {
		double currentAngleInRads = turtleListener.getAngle();
		double yDelta = pixels * Math.sin(currentAngleInRads);
		double xDelta = pixels * Math.cos(currentAngleInRads);
		turtleListener.setX(turtleListener.getX() + xDelta);
		turtleListener.setY(turtleListener.getY() + yDelta);
		return pixels;
	}

	private double moveBackward(double pixels) {
		forward(-pixels);
		return pixels;
	}

}
