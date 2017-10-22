package frontend.turtle_display;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TurtlePen {
	public static final String DEFAULT_TURTLE = "";
	
	private ImageView turtleImage;
	private double xCoordinate;
	private double yCoordinate;
	private double angle;
	
	public TurtlePen() {
		String imageName = DEFAULT_TURTLE;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		turtleImage = new ImageView(image); 
		xCoordinate = 0;
		yCoordinate = 0;
	}
	
	public void moveTurtle(double newXCoord, double newYCoord) {
		xCoordinate = newXCoord;
		yCoordinate = newYCoord;
	}
	
	public void rotateTurtle(double newAngle) {
		angle = newAngle;
		turtleImage.setRotate(newAngle - turtleImage.getRotate());
	}
	
	public double getXCoordinate() {
		return xCoordinate;
	}
	
	public double getYCoordinate() {
		return yCoordinate;
	}
	
	public double getAngle() { 
		return angle;
	}
	
	public ImageView getImage() {
		return turtleImage;
	}
}
