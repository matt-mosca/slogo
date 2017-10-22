package frontend.turtle_display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtlePen {
	public static final String DEFAULT_TURTLE = "Cartoon-Turtle.png";
	
	private ImageView turtleImage;
	private double xCoordinateOnScene;
	private double yCoordinateOnScene;
	private double angle;
	
	public TurtlePen(double xCoord, double yCoord) {
		String imageName = DEFAULT_TURTLE;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		turtleImage = new ImageView(image);
		turtleImage.setFitWidth(20);
		turtleImage.setFitHeight(25);
		xCoordinateOnScene = xCoord;
		yCoordinateOnScene = yCoord;
		turtleImage.setX(xCoord);
		turtleImage.setY(yCoord);
	}
	
	public void moveTurtle(double newXCoord, double newYCoord) {
		xCoordinateOnScene = newXCoord;
		yCoordinateOnScene = newYCoord;
		turtleImage.setX(newXCoord);
		turtleImage.setY(newYCoord);
	}
	
	public void rotateTurtle(double newAngle) {
		angle = newAngle;
		turtleImage.setRotate(newAngle - turtleImage.getRotate());
	}
	
	public double getXCoordinate() {
		return xCoordinateOnScene;
	}
	
	public double getYCoordinate() {
		return yCoordinateOnScene;
	}
	
	public double getAngle() { 
		return angle;
	}
	
	public ImageView getImage() {
		return turtleImage;
	}
}
