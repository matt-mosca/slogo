package frontend.turtle_display;

import apis.TurtleDisplay;

public class TurtleView implements TurtleDisplay{
	/**
     * Move a turtle's image to a new location within the pane.
     *
     * @param turtleIndex - identifies which turtle within the turtle collection to move
     * @param xCoordinate - the new x-coordinate of the turtle
     * @param yCoordinate - the new y-coordinate of the turtle
     */
    public void move(int turtleIndex, double xCoordinate, double yCoordinate) {
    	
    }

    /**
     * Change the direction to which the turtle's image points. Since coordinates are handled in the backend, this
     * direction only affects visualization (that is, the turtle only move in the direction it points to because of the
     * angle as stored in the backend).
     *
     * @param turtleIndex - identifies which turtle within the turtle collection to rotate
     * @param angle - the direction the turtle's image should point toward
     */
    public void rotate(int turtleIndex, double angle) {
    	
    }
}
