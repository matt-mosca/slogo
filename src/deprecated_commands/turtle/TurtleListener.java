package deprecated_commands.turtle;

import apis.TurtleDisplay;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * @deprecated
 */
public class TurtleListener {

	public static final double STARTING_ANGLE = Math.PI / 2;

	// For model bindings to view
	private static TurtleDisplay turtleDisplay;
	
	// Singleton TurtleListener can be changed to singleton List<TurtleListener> to
	// support future extension of multiple turtles
	private static TurtleListener turtleListener = null;


	private int turtleIndex; // to support multiple turtles in a future extension
	private DoubleProperty xProp = new SimpleDoubleProperty();
	private DoubleProperty yProp = new SimpleDoubleProperty();
	private DoubleProperty angleProp = new SimpleDoubleProperty();
	private boolean penUp;
	private boolean showing;

	public TurtleListener(int turtleIndex, TurtleDisplay turtleDisplay) {
		this.turtleDisplay = turtleDisplay;
		this.turtleIndex = turtleIndex;
		// Register listeners
		// Replace with a map of key-value pairs instead? but then how to dispatch right
		// method?
		angleProp.setValue(STARTING_ANGLE);
		registerXPropListener();
		registerYPropListener();
		registerAnglePropListener();		
	}

	// Pave way for construction of single instance from front end
	// Helps avoid propagation of TurtleDisplay to comamnds
	public static void initializeSingleton(TurtleDisplay turtleDisplay) {
		if (turtleListener == null) {
			turtleListener = new TurtleListener(0, turtleDisplay);
		}
	}
	
	public static TurtleListener getInstance() {
		return turtleListener;
	}
	
	void setX(double newX) {
		xProp.setValue(newX);
	}

	void setY(double newY) {
		yProp.setValue(newY);
	}

	void setAngle(double rad) {
		angleProp.setValue(rad);
	}

	// TODO - is there an instantaneous change in display upon setting pen-up/down?
	// If so, need front end to expose a public API method for showing / hiding pen
	void setPenUp(boolean penUp) {
		this.penUp = penUp;
	}

	// TODO - need front end to expose public API method for showing / hiding turtle
	// Call that method here
	void setShowing(boolean showing) {
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
	
	// TODO - uncomment when front end is ready
	private void registerXPropListener() {
		xProp.addListener((observableValue, oldValue, newValue) -> {
			//turtleDisplay.move(turtleIndex, (double) newValue, getY());
		});
	}

	// TODO - uncomment when front end is ready
	private void registerYPropListener() {
		yProp.addListener((observableValue, oldValue, newValue) -> {
			//turtleDisplay.move(turtleIndex, getX(), (double) newValue);
		});
	}

	// TODO - uncomment when front end is ready
	private void registerAnglePropListener() {
		angleProp.addListener((observableValue, oldValue, newValue) -> {
			//turtleDisplay.rotate(turtleIndex, (double) newValue);
		});
	}

}
