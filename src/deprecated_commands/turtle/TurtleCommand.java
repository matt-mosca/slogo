package deprecated_commands.turtle;

import deprecated_commands.AbstractCommandOld;

import java.lang.reflect.Method;

public class TurtleCommand extends AbstractCommandOld {

	private TurtleListener turtleListener = TurtleListener.getInstance();
	
	public TurtleCommand(Method methodToInvoke) {
		super(methodToInvoke);
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
