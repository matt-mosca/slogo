package apis;

/**
 * The pane of the display that holds the turtles and the paths they trace. Implementing class(es) will hold a
 * collection of ImageViews corresponding to the turtle objects in the backend.
 */
public interface TurtleDisplay {

    /**
     * Move a turtle's image to a new location within the pane.
     *
     * @param turtleIndex - identifies which turtle within the turtle collection to move
     * @param xCoordinate - the new x-coordinate of the turtle
     * @param yCoordinate - the new y-coordinate of the turtle
     */
    void move(int turtleIndex, double xCoord, double yCoord);

    /**
     * Change the direction to which the turtle's image points. Since coordinates are handled in the backend, this
     * direction only affects visualization (that is, the turtle only move in the direction it points to because of the
     * angle as stored in the backend).
     *
     * @param turtleIndex - identifies which turtle within the turtle collection to rotate
     * @param angle - the direction the turtle's image should point toward
     */
    void rotate(int turtleIndex, double angle);
    
    // TODO - index for multiple turtles??
    void pickUpPen(int turtleIndex);
    
    void putDownPen(int turtleIndex);
    
    void showTurtle(int turtleIndex);
    
    void hideTurtle(int turtleIndex);
}
