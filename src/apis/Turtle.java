package apis;

/**
 * Contains the backend representation of the displayed turtles. Handles updates of turtle's positions and heading.
 */
public interface Turtle {

    /**
     * Move in the direction the turtle currently points towards.
     *
     * @param amount - the magnitude by which the turtle moves
     */
    void move(double amount); // package private

    /**
     * Rotate the direction the turtle points  clockwise or counterclockwise.
     *
     * @param angle - positive (clockwise) or negative (counterclockwise) magnitude by which the turtle's heading is to
     *              be changed
     */
    void rotate(double angle); // package private

    /**
     * Rotate the turtle to an absolute heading (that is, regardless of its current heading).
     *
     * @param heading - the new direction to point the turtle towards
     */
    void setHeading(double heading); // package private

    /**
     * Points the turtle to face the point (x,y) where (0, 0) is the center of the screen
     *
     * @param x - the x-coordinate towards which the turtle should face
     * @param y - the y-coordinate towards which the turtle should face
     * @return the number of degrees turtle turned
     */
    double faceTowards(double x, double y); // package private

    /**
     * Move the turtle to a new absolute postion
     * @param x - the new x-coordinate for the turtle
     * @param y - the new x-coordinate for the turtle
     * @return the distance the turtle moved
     */
    double goTo(double x, double y); // package private
}

