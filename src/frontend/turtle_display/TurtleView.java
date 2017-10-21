package frontend.turtle_display;

import java.util.ArrayList;
import java.util.List;
import apis.TurtleDisplay;

public class TurtleView implements TurtleDisplay{
	private List<TurtlePen> displayedTurtles;
	
	public TurtleView() {
		displayedTurtles = new ArrayList<TurtlePen>();
		TurtlePen original = new TurtlePen();
		displayedTurtles.add(original);
	}
	
	/**
     * Move a turtle's image to a new location within the pane.
     *
     * @param turtleIndex - identifies which turtle within the turtle collection to move
     * @param xCoordinate - the new x-coordinate of the turtle
     * @param yCoordinate - the new y-coordinate of the turtle
     */
    public void move(int turtleIndex, double newXCoord, double newYCoord) {
    	//Be sure to check for errors in turtleIndex input here to avoid ArrayIndexOutOfBounds exceptions
    	displayedTurtles.get(turtleIndex).moveTurtle(newXCoord, newYCoord);
    }

    /**
     * Change the direction to which the turtle's image points. Since coordinates are handled in the backend, this
     * direction only affects visualization (that is, the turtle only move in the direction it points to because of the
     * angle as stored in the backend).
     *
     * @param turtleIndex - identifies which turtle within the turtle collection to rotate
     * @param angle - the direction the turtle's image should point toward
     */
    public void rotate(int turtleIndex, double newAngle) {
    	//Be sure to check for errors in turtleIndex input here to avoid ArrayIndexOutOfBounds exceptions
    	displayedTurtles.get(turtleIndex).rotateTurtle(newAngle);
    }
    
    public TurtlePen addTurtle() {
    	TurtlePen newAddition = new TurtlePen();
    	displayedTurtles.add(newAddition);
    	return newAddition;
    }
    
    //Make method that takes in the turtle display pane and attaches the TurtlePen objects in the 
    //list to that pane
}
