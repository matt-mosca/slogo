package frontend.turtle_display;

import apis.TurtleDisplay;
import frontend.window_setup.IDEWindow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class TurtleView implements TurtleDisplay{
	private List<TurtlePen> displayedTurtles;
	private Rectangle turtleField;
	private Pane layout;
	private double fieldCenterX;
	private double fieldCenterY;
	private Paint drawColor;
	private boolean isPenDown;
	
	public TurtleView(Pane border, Rectangle field) {
		displayedTurtles = new ArrayList<TurtlePen>();
		layout = border;
		turtleField = field;
		fieldCenterX = IDEWindow.LEFT_WIDTH + turtleField.getWidth() / 2;
		fieldCenterY = IDEWindow.TOP_HEIGHT + turtleField.getHeight() / 2;
		drawColor = Color.BLACK;
		isPenDown = true;
		TurtlePen original = new TurtlePen(fieldCenterX - TurtlePen.DEFAULT_WIDTH / 2,
				fieldCenterY - TurtlePen.DEFAULT_HEIGHT / 2);
		displayedTurtles.add(original);
	}
	
	public void displayInitialTurtle() {
		showTurtle(displayedTurtles.get(0));
	}
	
	public void showTurtle(TurtlePen turtle) {
    	layout.getChildren().add(turtle.getImage());
    }
	
	/**
     * Move a turtle's image to a new location within the pane.
     * @param turtleIndex - identifies which turtle within the turtle collection to move
     * @param xCoordinate - the new x-coordinate of the turtle
     * @param yCoordinate - the new y-coordinate of the turtle
     */
    public void move(int turtleIndex, double newXCoord, double newYCoord) {
    	//Be sure to check for errors in turtleIndex input here to avoid ArrayIndexOutOfBounds exceptions
    	double newXCoordinate = translateXCoord(newXCoord) - TurtlePen.DEFAULT_WIDTH / 2;
    	double newYCoordinate = translateYCoord(newYCoord) - TurtlePen.DEFAULT_HEIGHT / 2;
    	double currentLineXCoordinate = displayedTurtles.get(turtleIndex).getXCoordinate() + TurtlePen.DEFAULT_WIDTH / 2;
    	double currentLineYCoordinate = displayedTurtles.get(turtleIndex).getYCoordinate() + TurtlePen.DEFAULT_HEIGHT / 2;
    	displayedTurtles.get(turtleIndex).moveTurtle(newXCoordinate, newYCoordinate);
    	Drawer lineMaker = new Drawer(drawColor, isPenDown);
    	lineMaker.drawLine(currentLineXCoordinate, currentLineYCoordinate, translateXCoord(newXCoord), translateYCoord(newYCoord),
    			layout);
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
    	TurtlePen newAddition = new TurtlePen(fieldCenterX - TurtlePen.DEFAULT_WIDTH / 2, fieldCenterY - TurtlePen.DEFAULT_HEIGHT / 2);
    	displayedTurtles.add(newAddition);
    	showTurtle(newAddition);
    	return newAddition;
    }
    
	public void pickUpPen() {
		isPenDown = false;
	}
	
	public void putDownPen() {
		isPenDown = true;
	}
	
	public void changeDrawColor(Paint color) {
		drawColor = color;
	}
    
    /**
     * Translates the x-coordinate relative to the center (input from backend) into the x-coordinate
     * on the scene
     * @param coordFromCenter
     * @return x-coordinate along the field
     */
    private double translateXCoord(double xCoordFromCenter) {
    	return fieldCenterX + xCoordFromCenter;
    }
    
    /**
     * Translates the y-coordinate relative to the center (input from backend) into the y-coordinate
     * on the scene
     * @param coordFromCenter
     * @return x-coordinate along the field
     */
    private double translateYCoord(double yCoordFromCenter) {
    	return fieldCenterY - yCoordFromCenter;
    }
}
