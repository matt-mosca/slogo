package frontend.turtle_display;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import apis.TurtleDisplay;
import frontend.window_setup.IDEWindow;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 * TurtleView.java
 * @author Matthew Mosca
 * Deals with the display and movement of all the turtles in the program and their pens. This class 
 * communicates with the back end through the controller about information related to the properties of
 * the turtles. An instance of this class holds a list of the turtles currently displayed. The class is
 * observed by IDEWindow so that changes to the properties of each turtle in the display will notify
 * IDEWindow so that the new property values can be displayed.
 * @version 11.03.17
 */

public class TurtleView extends Observable implements TurtleDisplay {

	public static final double ACTIVE_OPACITY = 1.0;
	public static final double INACTIVE_OPACITY = 0.5;

	private List<TurtlePen> displayedTurtles;
	private Rectangle turtleField;
	private Pane layout;
	private double fieldCenterX;
	private double fieldCenterY;
	private ObjectProperty<Color> currentPenColor;
	
	/**
	 * Constructor for class TurtleView. Initializes instance variables and sets some to default values,
	 * and adds the first TurtlePen object (representing the turtle) to the list of turtles to be 
	 * displayed.
	 * @param border - the Pane on which the different parts of the program's display are arranged
	 * @param field - the Rectangle turtle field around which the displayed turtles move
	 */
	public TurtleView(Pane border, Rectangle field) {
		displayedTurtles = new ArrayList<TurtlePen>();
		turtleField = field;
		layout = border;
		fieldCenterX = IDEWindow.LEFT_WIDTH + turtleField.getWidth() / 2;
		fieldCenterY = IDEWindow.TOP_HEIGHT + turtleField.getHeight() / 2;
		currentPenColor = new SimpleObjectProperty<>();
		TurtlePen original = new TurtlePen(fieldCenterX - TurtlePen.DEFAULT_WIDTH / 2,
				fieldCenterY - TurtlePen.DEFAULT_HEIGHT / 2);
		displayedTurtles.add(original);
		currentPenColor.setValue(TurtlePen.DEFAULT_COLOR);
	}
	
	/**
	 * Calls a method to add the first turtle to the scene.
	 */
	public void displayInitialTurtle() {
		displayTurtle(displayedTurtles.get(0));
	}
	
	/**
	 * A method that executes some event when the ImageView representation of a given turtle is clicked.
	 * The exact action/event that is executed is supplied in the controller, where this method is called.
	 * In this program, it serves to change the graphical representation of turtles that are made active
	 * through a mouse click.
	 * @param turtleIndex - the index of the turtle
	 * @param action - the event that is put into effect when this method is called
	 */
	public void selectTurtleOnClick(int turtleIndex, EventHandler<? super MouseEvent> action) {
		displayedTurtles.get(turtleIndex).getImage().setOnMouseClicked(action);
	}
	
	/**
	 * Iterates through the list of turtles and sets those that are active to be fully one level of opacity,
	 *  while at the same time setting those that are inactive to a different level of opacity, so that 
	 *  the two are visually distinct. This method is called whenever the active turtles change.
	 * @param activeTurtles - the currently active turtles, which will execute whatever command is entered
	 */
	public void changeRepresentationOfActive(List<Integer> activeTurtles) {
		for(int i = 0; i < displayedTurtles.size(); i++) {
			if(activeTurtles.contains(i))
				displayedTurtles.get(i).getImage().setOpacity(ACTIVE_OPACITY);
			else
				displayedTurtles.get(i).getImage().setOpacity(INACTIVE_OPACITY);
		}
	}
	
	/**
	 * Adds a turtle to the scene so that it is displayed.
	 * @param turtle - the turtle to be added
	 */
	private void displayTurtle(TurtlePen turtle) {
    		layout.getChildren().add(turtle.getImage());
    }
	
	/**
	 * Calls a method to display the turtle at position turtleIndex in the list of turtles.
	 * @param turtleIndex - the index of the turtle to be displayed
	 */
	@Override
	public void showTurtle(int turtleIndex) {
		displayTurtle(displayedTurtles.get(turtleIndex));
	}
	
	/**
	 * Removes a turtle from the scene.
	 * @param turtle - the turtle to be removed
	 */
	private void removeFromDisplay(TurtlePen turtle) {
		layout.getChildren().remove(turtle.getImage());
	}
	
	/**
	 * Calls a method to hide from view the turtle at position turtleIndex in the list of turtles.
	 * @param turtleIndex - the index of the turtle to be hidden
	 */
	@Override
	public void hideTurtle(int turtleIndex) {
		removeFromDisplay(displayedTurtles.get(turtleIndex));
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
    	setChanged();
    	notifyObservers();
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
    	double processedAngle = BackendValProcessor.processAngle(newAngle);
    	displayedTurtles.get(turtleIndex).rotateTurtle(processedAngle);
    	setChanged();
    	notifyObservers();
    }
    
    /**
     * Adds a new turtle to the list of displayed turtles and calls a method to display the turtle on 
     * the scene.
     * @return the the turtle that is added
     */
    public TurtlePen addTurtle() {
    	TurtlePen newAddition = new TurtlePen(fieldCenterX - TurtlePen.DEFAULT_WIDTH / 2, fieldCenterY - TurtlePen.DEFAULT_HEIGHT / 2);
    	displayedTurtles.add(newAddition);
    	displayTurtle(newAddition);
    	setChanged();
    	notifyObservers();
    	return newAddition;
    }
    
    /**
     * Picks up the pen of the turtle at position turtleIndex in the list of displayed turtles so that
     * this turtle does not make a line when it moves.
     * @param turtleIndex - the index of the turtle that should be acted upon by this method
     */
    @Override
	public void pickUpPen(int turtleIndex) {
		displayedTurtles.get(turtleIndex).pickUpPen();
		setChanged();
		notifyObservers();
	}
	
    /**
     * Puts down the pen of the turtle at position turtleIndex in the list of displayed turtles so that
     * this turtle makes a line when it moves.
     * @param turtleIndex - the index of the turtle that should be acted upon by this method
     */
    @Override
	public void putDownPen(int turtleIndex) {
		displayedTurtles.get(turtleIndex).putDownPen();
		setChanged();
		notifyObservers();
	}
	
    /**
     * Changes the color of the line made by the turtle at position turtleIndex in the list of displayed
     * turtles.
     * @param turtleIndex - the index of the turtle acted upon by this method
     * @param newColor - the new color of the line for this turtle
     */
	public void changeDrawColor(int turtleIndex, Color newColor) {
    	currentPenColor.setValue(newColor);
		displayedTurtles.get(turtleIndex).setPenColor(newColor);
		setChanged();
		notifyObservers();
	}

	/**
	 * Changes the width of the line made by the movement of the turtle at position turtleIndex in the
	 * list of displayed turtles.
	 * @param turtleIndex - the index of the turtle acted upon by this method
	 * @param newWidth - the new width of the line made by this turtle
	 */
	public void changeStrokeWidth(int turtleIndex, double newWidth) {
		displayedTurtles.get(turtleIndex).setStrokeWidth(newWidth);
		setChanged();
		notifyObservers();
	}

	/**
	 * Changes the image used to represent the turtle at position turtleIndex in the list of displayed
	 * turtles
	 * @param turtleIndex - the index of the turtle acted upon by this method
	 * @param newImage - the new image to be used to represent the indicated turtle
	 */
	public void changeImage(int turtleIndex, Image newImage) {
		displayedTurtles.get(turtleIndex).changeImage(newImage);
	}

	/**
	 * Method used in backend with undo/redo function to revert the turtle display to its original state.
	 */
	public void clear() {
		TurtlePen original = new TurtlePen(fieldCenterX - TurtlePen.DEFAULT_WIDTH / 2,
				fieldCenterY - TurtlePen.DEFAULT_HEIGHT / 2);
		displayedTurtles.forEach(turtlePen -> {
			layout.getChildren().remove(turtlePen.getImage());
			layout.getChildren().removeAll(layout.getChildren().filtered(node ->  node instanceof Line));
		});
		displayedTurtles.clear();
		displayedTurtles.add(original);
		displayInitialTurtle();
	}

	/**
	 * Getter to return ObjectProperty representing current pen color.
	 * @return the current pen color
	 */
	public ObjectProperty<Color> getCurrentPenColorProperty() { 
		return currentPenColor; 
	}
	
	/**
	 * Getter to return the list of displayed turtles.
	 * @return the list of displayed turtles.
	 */
	public List<TurtlePen> getDisplayedTurtles() {
		return displayedTurtles;
	}
}
