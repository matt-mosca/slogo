package frontend.turtle_display;

import backend.Controller;

/**
 * TurtleGraphicalControls.java
 * @author Matthew Mosca
 * Communicates with the constructor to make changes to the turtles in the backend when certain actions
 * happen in the frontend as a result of user interaction with the GUI.
 * @version 11.03.17
 */
public class TurtleGraphicalControls {
	public static final double STANDARD_MOVE = 10;
	public static final double STANDARD_TURN = 10;

	Controller turtleControl;

	/**
	 * Constructor for class TurtleGraphicalControls.
	 * @param control - the controller 
	 */
	public TurtleGraphicalControls(Controller control) {
		turtleControl = control;
	}

	/**
	 * Calls a controller method to move the active turtle(s) forward a standard amount.
	 */
	public void moveForward() {
		turtleControl.moveTurtlesForward(STANDARD_MOVE);
	}

	/**
	 * Calls a controller method to move the active turtle(s) backward a standard amount.
	 */
	public void moveBackward() {
		turtleControl.moveTurtlesBackward(STANDARD_MOVE);
	}

	/**
	 * Calls a controller method to rotate the active turtle(s) right a standard amount.
	 */
	public void rotateRight() {
		turtleControl.turnTurtlesRight(STANDARD_TURN);
	}

	/**
	 * Calls a controller method to rotate the active turtle(s) left a standard amount.
	 */
	public void rotateLeft() {
		turtleControl.turnTurtlesLeft(STANDARD_TURN);
	}
}
