package frontend.turtle_display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * TurtlePen.java
 * @author Matthew Mosca
 * Used to visually represent a turtle in the program.
 * @version 11.03.17
 */
public class TurtlePen {
	public static final String DEFAULT_TURTLE = "Cartoon-Turtle.png";
	public static final double DEFAULT_WIDTH = 20;
	public static final double DEFAULT_HEIGHT = 25;
	public static final Color DEFAULT_COLOR = Color.BLACK;
	private double DEFAULT_STROKE_WIDTH = 1.0;
	
	private ImageView turtleImage;
	private double xCoordinateOnRegion;
	private double yCoordinateOnRegion;
	private double angle;
	private Color penColor;
	private boolean isPenDown;
	private double strokeWidth;
	
	/**
	 * Constructor for class TurtlePen.
	 * @param xCoord - the initial x-coordinate of the ImageView, in terms of its position on the entire
	 * scene
	 * @param yCoord - the initial y-coordinate of the ImageView, in terms of its position on the entire
	 * scene
	 */
	public TurtlePen(double xCoord, double yCoord) {
		String imageName = DEFAULT_TURTLE;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		turtleImage = new ImageView(image);
		turtleImage.setFitWidth(DEFAULT_WIDTH);
		turtleImage.setFitHeight(DEFAULT_HEIGHT);
		xCoordinateOnRegion = xCoord;
		yCoordinateOnRegion = yCoord;
		turtleImage.setX(xCoord);
		turtleImage.setY(yCoord);
		penColor = DEFAULT_COLOR;
		isPenDown = true;
		strokeWidth = DEFAULT_STROKE_WIDTH;	
	}
	
	/**
	 * Moves the ImageView to a new position specified by the parameters.
	 * @param newXCoord - the x-coordinate to which the ImageView should move
	 * @param newYCoord - the y-coordinate to which the ImageView should move
	 */
	void moveTurtle(double newXCoord, double newYCoord) {
		xCoordinateOnRegion = newXCoord;
		yCoordinateOnRegion = newYCoord;
		turtleImage.setX(newXCoord);
		turtleImage.setY(newYCoord);
	}
	
	/**
	 * Rotates the ImageView to a new angle specified by the parameter.
	 * @param newAngle - the new angle to which the ImageView should rotate
	 */
	void rotateTurtle(double newAngle) {
		turtleImage.setRotate(newAngle);
		angle = newAngle;
	}
	
	/**
	 * Getter to return the x-coordinate of the ImageView.
	 * @return the x-coordinate of the ImageView
	 */
	public double getXCoordinate() {
		return xCoordinateOnRegion;
	}
	
	/**
	 * Getter to return the y-coordinate of the ImageView.
	 * @return the y-coordinate of the ImageView
	 */
	public double getYCoordinate() {
		return yCoordinateOnRegion;
	}
	
	/**
	 * Getter to return the angle of the ImageView.
	 * @return the angle of the ImageView
	 */
	public double getAngle() { 
		return angle;
	}
	
	/**
	 * Getter to return the ImageView that represents the turtle.
	 * @return the ImageView representing the turtle
	 */
	ImageView getImage() {
		return turtleImage;
	}
	
	/**
	 * Sets the visual representation of the turtle to be a new image, and resizes the image appropriately.
	 * @param newTurtleImage - the new image to represent the turtle
	 */
	void changeImage(Image newTurtleImage) {
		turtleImage.setImage(newTurtleImage);
		turtleImage.setFitWidth(DEFAULT_WIDTH);
		turtleImage.setFitHeight(DEFAULT_HEIGHT);
	}
	
	/**
	 * Sets the boolean instance variable to indicate that the pen should be put down, and that this 
	 * turtle should make a line when it moves.
	 */
	void putDownPen() {
		isPenDown = true;
	}
	
	/**
	 * Sets the boolean instance variable to indicate that the pen should be up, and that this turtle 
	 * should not make a line when it moves.
	 */
	void pickUpPen() {
		isPenDown = false;
	}
	
	/**
	 * Setter to change the color of the line made by the turtle to be a new color.
	 * @param newColor - the new color that the line made by the turtle should be set to
	 */
	void setPenColor(Color newColor) { 
		penColor = newColor; 
	}
	
	/**
	 * Getter to return whether the pen is down.
	 * @return boolean representing whether or not the pen is down
	 */
	public boolean getIsPenDown() {
		return isPenDown;
	}
	
	/**
	 * Getter to return the color of the line made by this turtle.
	 * @return the color of this turtle's pen
	 */
	public Color getPenColor() { 
		return penColor; 
	}
	
	/**
	 * Setter to change the width of the line made by this turtle.
	 * @param newWidth - the new width for the line
	 */
	void setStrokeWidth(double newWidth) {
		strokeWidth = newWidth;
	}
	
	/**
	 * Getter to return the width of the line made by this turtle.
	 * @return the width of the line made by this turtle
	 */
	public double getStrokeWidth() {
		return strokeWidth;
	}
}
