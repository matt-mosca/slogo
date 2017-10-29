package frontend.turtle_display;

import backend.Controller;
import backend.error_handling.TurtleOutOfScreenException;

public class TurtleGraphicalControls {
	public static final double STANDARD_MOVE = 10;
	public static final double STANDARD_TURN = 10;
	
	Controller turtleControl;
	public TurtleGraphicalControls(Controller control) {
		turtleControl = control;
	}
	public void moveForward() {
		try {
			turtleControl.moveTurtlesForward(STANDARD_MOVE);
		}
		catch(TurtleOutOfScreenException e) {
			moveBackward();
		}
	}
	public void moveBackward() {
		try {
			turtleControl.moveTurtlesBackward(STANDARD_MOVE);
		}
		catch(TurtleOutOfScreenException e) {
			moveForward();
		}
	}
	public void rotateRight() {
		turtleControl.turnTurtlesRight(STANDARD_TURN);
	}
	public void rotateLeft() {
		turtleControl.turnTurtlesLeft(STANDARD_TURN);
	}
}
