package backend.turtle;

/**
 * @author Adithya Raghunathan
 */
public class Turtle {

	public static final double STARTING_ANGLE = Math.PI / 2;
	public static final double ONE_REVOLUTION_DEGREES = 360;

	private double xCoord;
	private double yCoord;
	private double angle;

	private boolean penUp;
	private boolean showing;

	public Turtle() {
		angle = STARTING_ANGLE;
		showing = true;
	}

	double moveForward(double pixels) {
		double currentAngleInRads = angle;
		// To avoid imprecision of cos 90, sin 0, etc
		double yDelta = currentAngleInRads == 0 || currentAngleInRads == Math.PI ? 0
				: pixels * Math.sin(currentAngleInRads);
		double xDelta = currentAngleInRads == Math.PI / 2 || currentAngleInRads == 3 * Math.PI / 2 ? 0
				: pixels * Math.cos(currentAngleInRads);
		setX(getX() + xDelta);
		setY(getY() + yDelta);
		return pixels;
	}

	double rotate(boolean clockwise, double angleInDegrees) {
		double currentAngleInDegrees = Math.toDegrees(angle);
		setAngle(clockwise ? currentAngleInDegrees - angleInDegrees : currentAngleInDegrees + angleInDegrees);
		return angleInDegrees;
	}

	double setAngle(double angleInDegrees) {
		double angleInRad = Math.toRadians(angleInDegrees);
		double degreesMoved = (angleInDegrees - Math.toDegrees(angle));
		angle = angleInRad;
		return degreesMoved;
	}

	double setTowards(double x, double y) {
		double xDelta = x - getX();
		double yDelta = y - getY();
		double angleToFaceInRad = Math.atan2(yDelta, xDelta);
		double angleToTurnInRad = angleToFaceInRad - angle;
		// Angles measured anti-clockwise by default
		return rotate(false, Math.toDegrees(angleToTurnInRad));
	}

	double setXY(double x, double y) {
		setX(x);
		setY(y);
		return Math.sqrt(x * x + y * y);
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

	double getAngleInDegrees() {
		return Math.toDegrees(getAngle());
	}

	boolean isPenUp() {
		return penUp;
	}

	boolean isShowing() {
		return showing;
	}

	double getAngle() {
		return angle;
	}

	// TODO - handle going off-screen
	private void setX(double newX) {
		xCoord = newX;
	}

	// TODO - handle going off-screen
	private void setY(double newY) {
		yCoord = newY;
	}

}
