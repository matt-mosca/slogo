package commands.turtle;

import apis.TurtleDisplay;
import commands.AbstractCommand;
import javafx.beans.property.DoubleProperty;

public class TurtleListener {

	public static final double STARTING_ANGLE = Math.PI / 2;

	// For model bindings to view
	private TurtleDisplay turtleDisplay;

	private int turtleIndex; // to support multiple turtles in a future extension
	private DoubleProperty xProp;
	private DoubleProperty yProp;
	private DoubleProperty angleProp;
	private boolean penUp;
	private boolean showing;

	public TurtleListener(int turtleIndex) {
		this.turtleIndex = turtleIndex;
		// Register listeners
		// Replace with a map of key-value pairs instead? but then how to dispatch right
		// method?
		angleProp.setValue(STARTING_ANGLE);
		registerXPropListener();
		registerYPropListener();
		registerAnglePropListener();
	}

	public class TurtleCommand extends AbstractCommand {

		private TurtleListener turtleListener;

		public TurtleCommand(Class commandType, String methodToInvoke, int numberOfDoubleParameters,
				TurtleListener turtleListener) throws NoSuchMethodException {
			super(commandType, methodToInvoke, numberOfDoubleParameters);
			this.turtleListener = turtleListener;
		}

		// Add bindings to front end's turtle representation using front end external
		// API methods

		private double forward(double pixels) {
			double currentAngleInRads = angleProp.getValue();
			double yDelta = pixels * Math.sin(currentAngleInRads);
			double xDelta = pixels * Math.cos(currentAngleInRads);
			setX(getX() + xDelta);
			setY(getY() + yDelta);
			return pixels;
		}

		private double moveBackward(double pixels) {
			forward(-pixels);
			return pixels;
		}

	}

	private void setX(double newX) {
		xProp.setValue(newX);
	}

	private void setY(double newY) {
		yProp.setValue(newY);
	}

	private void setAngle(double rad) {
		angleProp.setValue(rad);
	}

	private void setPenUp(boolean penUp) {
		this.penUp = penUp;
	}

	private void setShowing(boolean showing) {
		this.showing = showing;
	}

	// Used for Turtle Queries, leave package-private for now

	// All coordinates are from center of screen
	double getX() {
		return xProp.getValue();
	}

	double getY() {
		return yProp.getValue();
	}

	double getAngle() {
		return angleProp.getValue();
	}

	boolean isPenUp() {
		return penUp;
	}

	boolean isShowing() {
		return showing;
	}

	private void registerXPropListener() {
		xProp.addListener((observableValue, oldValue, newValue) -> {
			turtleDisplay.move(turtleIndex, (double) newValue, getY());
		});
	}

	private void registerYPropListener() {
		yProp.addListener((observableValue, oldValue, newValue) -> {
			turtleDisplay.move(turtleIndex, getX(), (double) newValue);
		});
	}

	private void registerAnglePropListener() {
		angleProp.addListener((observableValue, oldValue, newValue) -> {
			turtleDisplay.rotate(turtleIndex, (double) newValue);
		});
	}

}
