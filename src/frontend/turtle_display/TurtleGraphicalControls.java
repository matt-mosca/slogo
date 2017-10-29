package frontend.turtle_display;

import backend.Controller;

public class TurtleGraphicalControls {
	public static final double STANDARD_MOVE = 10;
	public static final double STANDARD_TURN = 10;
	
	Controller turtleControl;
	public TurtleGraphicalControls(Controller control) {
		turtleControl = control;
	}
	public void moveForward() {
		turtleControl.moveTurtlesForward(STANDARD_MOVE);
	}
	public void moveBackward() {
		turtleControl.moveTurtlesBackward(STANDARD_MOVE);
	}
	public void rotateRight() {
		turtleControl.turnTurtlesRight(STANDARD_TURN);
	}
	public void rotateLeft() {
		turtleControl.turnTurtlesLeft(STANDARD_TURN);
	}
}
