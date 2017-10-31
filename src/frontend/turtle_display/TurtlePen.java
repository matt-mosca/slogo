package frontend.turtle_display;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
	
	void moveTurtle(double newXCoord, double newYCoord) {
		xCoordinateOnRegion = newXCoord;
		yCoordinateOnRegion = newYCoord;
		turtleImage.setX(newXCoord);
		turtleImage.setY(newYCoord);
	}
	
	void rotateTurtle(double newAngle) {
		turtleImage.setRotate(newAngle);
		angle = newAngle;
	}
	
	double getXCoordinate() {
		return xCoordinateOnRegion;
	}
	
	double getYCoordinate() {
		return yCoordinateOnRegion;
	}
	
	double getAngle() { 
		return angle;
	}
	
	ImageView getImage() {
		return turtleImage;
	}
	
	void changeImage(Image newTurtle) {
		turtleImage.setImage(newTurtle);
		turtleImage.setFitWidth(DEFAULT_WIDTH);
		turtleImage.setFitHeight(DEFAULT_HEIGHT);
	}
	
	void putDownPen() {
		isPenDown = true;
	}
	
	void pickUpPen() {
		isPenDown = false;
	}
	
	void setPenColor(Color color) { penColor = color; }
	
	boolean getIsPenDown() {
		return isPenDown;
	}
	
	Color getPenColor() { return penColor; }
	
	void setStrokeWidth(double width) {
		strokeWidth = width;
	}
	
	double getStrokeWidth() {
		return strokeWidth;
	}
}
