package frontend.turtle_display;

import apis.TurtleDisplay;
import frontend.window_setup.IDEWindow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class TurtleView implements TurtleDisplay{

	public static final double DEFAULT_WIDTH = 20;
	public static final double DEFAULT_HEIGHT = 25;

	private List<TurtlePen> displayedTurtles;
	private Rectangle turtleField;
	private Pane layout;
	private double fieldCenterX;
	private double fieldCenterY;
	
	public TurtleView(Pane border, Rectangle field) {
		displayedTurtles = new ArrayList<TurtlePen>();
		layout = border;
		turtleField = field;
		fieldCenterX = IDEWindow.LEFT_WIDTH + turtleField.getWidth() / 2;
		fieldCenterY = IDEWindow.TOP_HEIGHT + turtleField.getHeight() / 2;
		TurtlePen original = new TurtlePen(fieldCenterX - TurtlePen.DEFAULT_WIDTH / 2,
				fieldCenterY - TurtlePen.DEFAULT_HEIGHT / 2);
		displayedTurtles.add(original);
	}
	
	public void displayInitialTurtle() {
		showTurtle(displayedTurtles.get(0));
	}
	
	// TODO - possible to make this package-friendly instead of public?
	// Perhaps by reorganizing front end packages?
	public void showTurtle(TurtlePen turtle) {
    		layout.getChildren().add(turtle.getImage());
    }
	
	@Override
	public void showTurtle(int index) {
		showTurtle(displayedTurtles.get(index));
	}
	
	public void hideTurtle(TurtlePen turtle) {
		layout.getChildren().remove(turtle.getImage());
	}
	
	@Override
	public void hideTurtle(int index) {
		hideTurtle(displayedTurtles.get(index));
	}
	
	/**
     * Move a turtle's image to a new location within the pane.
     * @param turtleIndex - identifies which turtle within the turtle collection to move
     * @param xCoordinate - the new x-coordinate of the turtle
     * @param yCoordinate - the new y-coordinate of the turtle
     */
    public void move(int turtleIndex, double newXCoord, double newYCoord) {
    	TurtlePen current = displayedTurtles.get(turtleIndex);
    	double newXCoordinate = BackendValProcessor.translateXCoord(fieldCenterX, newXCoord) - TurtlePen.DEFAULT_WIDTH / 2;
    	double newYCoordinate = BackendValProcessor.translateYCoord(fieldCenterY, newYCoord) - TurtlePen.DEFAULT_HEIGHT / 2;
    	double currentLineXCoordinate = current.getXCoordinate() + TurtlePen.DEFAULT_WIDTH / 2;
    	double currentLineYCoordinate = current.getYCoordinate() + TurtlePen.DEFAULT_HEIGHT / 2;
    	current.moveTurtle(newXCoordinate, newYCoordinate);
    	Drawer lineMaker = new Drawer(current.getPenColor(), current.getStrokeWidth(), current.getIsPenDown());
    	lineMaker.drawLine(currentLineXCoordinate, currentLineYCoordinate, BackendValProcessor.translateXCoord(
    			fieldCenterX, newXCoord), BackendValProcessor.translateYCoord(fieldCenterY, newYCoord),layout);
//    	System.out.println("New x of turtle " + turtleIndex + " : " + displayedTurtles.get(turtleIndex).getXCoordinate());
//    	System.out.println("New y of turtle " + turtleIndex + " : " + displayedTurtles.get(turtleIndex).getYCoordinate());
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
    	double processedAngle = BackendValProcessor.processAngle(newAngle);
    	displayedTurtles.get(turtleIndex).rotateTurtle(processedAngle);
    	System.out.println("New angle of turtle " + " index " + displayedTurtles.get(turtleIndex).getAngle());
    }
    
    public TurtlePen addTurtle() {
    	TurtlePen newAddition = new TurtlePen(fieldCenterX - TurtlePen.DEFAULT_WIDTH / 2, fieldCenterY - TurtlePen.DEFAULT_HEIGHT / 2);
    	displayedTurtles.add(newAddition);
    	showTurtle(newAddition);
    	return newAddition;
    }
    
    /*public void changeI(Image newTurtle) {
		tur= new ImageView(newTurtle);
		turtleImage.setFitWidth(DEFAULT_WIDTH);
		turtleImage.setFitHeight(DEFAULT_HEIGHT);
	}*/
    
    @Override
	public void pickUpPen(int turtleIndex) {
		displayedTurtles.get(turtleIndex).pickUpPen();
	}
	
    @Override
	public void putDownPen(int turtleIndex) {
		displayedTurtles.get(turtleIndex).putDownPen();
	}
	
	public void changeDrawColor(int turtleIndex, Paint color) {
		displayedTurtles.get(turtleIndex).setPenColor(color);
	}

	public void changeStrokeWidth(int turtleIndex, double width) { 
		displayedTurtles.get(turtleIndex).setStrokeWidth(width);
	}
	//Change from 0 to selected index
	//Make conditional, so that you do not have to select the turtle if there is only one
	public void changeImage(Image image) {
		displayedTurtles.get(0).changeImage(image);
	}
}
