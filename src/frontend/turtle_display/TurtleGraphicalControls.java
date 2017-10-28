package frontend.turtle_display;

import backend.Controller;

public class TurtleGraphicalControls {
	Controller turtleControl;
	public TurtleGraphicalControls(Controller control) {
		turtleControl = control;
	}
	public void moveForward(double distance) {
		turtleControl.moveTurtlesForward(distance);
	}
	public void moveBackward(double distance) {
		turtleControl.moveTurtlesBackward(distance);
	}
	public void rotateRight(double degrees) {
		turtleControl.turnTurtlesRight(degrees);
	}
	public void rotateLeft(double degrees) {
		turtleControl.turnTurtlesLeft(degrees);
	}
}
