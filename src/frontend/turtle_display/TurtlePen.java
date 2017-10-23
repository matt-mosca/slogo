package frontend.turtle_display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtlePen {
	public static final String DEFAULT_TURTLE = "Cartoon-Turtle.png";
	public static final double DEFAULT_WIDTH = 20;
	public static final double DEFAULT_HEIGHT = 25;
	
	private ImageView turtleImage;
	private double xCoordinateOnRegion;
	private double yCoordinateOnRegion;
	private double angle;
	
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
	}
	
	public void moveTurtle(double newXCoord, double newYCoord) {
		xCoordinateOnRegion = newXCoord;
		yCoordinateOnRegion = newYCoord;
		turtleImage.setX(newXCoord);
		turtleImage.setY(newYCoord);
	}
	
	public void rotateTurtle(double newAngle) {
		angle = newAngle;
		turtleImage.setRotate(newAngle - turtleImage.getRotate());
	}
	
	public double getXCoordinate() {
		return xCoordinateOnRegion;
	}
	
	public double getYCoordinate() {
		return yCoordinateOnRegion;
	}
	
	public double getAngle() { 
		return angle;
	}
	
	public ImageView getImage() {
		return turtleImage;
	}
	public void setImage(Image newTurtle) {
		turtleImage = new ImageView(newTurtle);
		turtleImage.setFitWidth(25);
		turtleImage.setFitHeight(30);
	}
}
