package backend.turtle;

public class Turtle {

	public static final double STARTING_ANGLE = Math.PI / 2;
	
	private double xCoord;
	private double yCoord;
	private double angle;
	
	private boolean penUp;
	private boolean showing;

	public Turtle() {
		angle = STARTING_ANGLE;
	}
	
	double moveForward(double pixels) {
		double currentAngleInRads = angle;
		double yDelta = pixels * Math.sin(currentAngleInRads);
		double xDelta = pixels * Math.cos(currentAngleInRads);
		setX(getX() + xDelta);
		setY(getY() + yDelta);
		return pixels;
	}

	void setPenUp(boolean penUp) {
		this.penUp = penUp;
	}

	void setShowing(boolean showing) {
		this.showing = showing;
	}

	// Used for Turtle Queries, leave package-private for now

	// All coordinates are from center of screen
	double getX() {
		return xCoord;
	}

	double getY() {
		return yCoord;
	}

	double getAngle() {
		return angle;
	}

	boolean isPenUp() {
		return penUp;
	}

	boolean isShowing() {
		return showing;
	}
	
	private void setX(double newX) {
		xCoord = newX;
	}

	private void setY(double newY) {
		yCoord = newY;
	}

	private void setAngle(double rad) {
		angle = rad;
	}

}
