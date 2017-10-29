package backend.turtle;

import frontend.turtle_display.TurtleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntToDoubleFunction;

//import backend.error_handling.TurtleOutOfScreenException;

public class TurtleFactory {

	private TurtleView turtleView;
	private double xBounds;
	private double yBounds;

	private List<Turtle> createdTurtles;

	private int activeTurtleId;
	private int queryTurtleId; // Used when turtle to be queried is different from active turtle
	// For O(1) checking of duplicates while preserving insertion order
	private LinkedHashSet<Integer> toldTurtleIds;

	public TurtleFactory(TurtleView turtleView, double xBounds, double yBounds) {
		this.turtleView = turtleView;
		this.xBounds = xBounds;
		this.yBounds = yBounds;
		createdTurtles = new ArrayList<>();
		Turtle firstTurtle = new Turtle();
		activeTurtleId = 1;
		queryTurtleId = 0;
		createdTurtles.add(firstTurtle);
		toldTurtleIds = new LinkedHashSet<Integer>(Arrays.asList(new Integer[] { activeTurtleId }));
	}

	// TELL [ 100 ] -- creates all turtles up to 100
	void addTurtles(int turtleId) {
		for (int newTurtleId = createdTurtles.size() + 1; newTurtleId <= turtleId; newTurtleId++) {
			Turtle newTurtle = new Turtle();
			createdTurtles.add(newTurtle);
			turtleView.addTurtle();
		}
	}

	// TURTLES
	int getNumberTurtlesCreated() {
		return createdTurtles.size();
	}

	// ASK [ turtle(s) ]
	List<Turtle> getTurtlesFromIdList(List<Integer> idList) {
		List<Turtle> turtles = new ArrayList<>();
		for (int id : idList) {
			Turtle turtleWithId = getTurtle(id);
			turtles.add(turtleWithId);
		}
		return turtles;
	}

	// ASK [ turtle ] or something similar
	// Convert from 1-based indexing to 0-based indexing
	Turtle getTurtle(int id) {
		if (id > getNumberTurtlesCreated()) {
			addTurtles(id);
		}
		return createdTurtles.get(getZeroBasedId(id));
	}

	Turtle getActiveTurtle() {
		return getTurtle(activeTurtleId);
	}

	int getActiveTurtleId() {
		return activeTurtleId;
	}

	Turtle getQueryTurtle() {
		return getTurtle(queryTurtleId);
	}

	int getQueryTurtleId() {
		return queryTurtleId >= 1 ? queryTurtleId : activeTurtleId;
	}

	// id has to exist or be 0 (to mark end of query so that subsequent queries
	// reference activeTurtleId)
	// return true if successfully set, else false
	boolean setQueryTurtleId(int id) {
		if (id < 0 || id > createdTurtles.size()) {
			return false;
		}
		queryTurtleId = id;
		return true;
	}

	private double doForTurtles(IntToDoubleFunction forEachTurtle, Collection<Integer> turtles) {
		double result = 0;
		for (int toldTurtleId : turtles) {
			activeTurtleId = toldTurtleId;
			result = forEachTurtle.applyAsDouble(toldTurtleId);
		}
		return result;
	}

	private double doForToldTurtles(IntToDoubleFunction forEachTurtle) {
		return doForTurtles(forEachTurtle, toldTurtleIds);
	}

	double setActiveTurtles(Integer[] ids) {
		toldTurtleIds.clear();
		toldTurtleIds.addAll(Arrays.asList(ids));
		for (int turtleId : toldTurtleIds) {
			addTurtles(turtleId);
		}
		activeTurtleId = ids.length > 0 ? ids[ids.length - 1] : 0;
		System.out.println("Set activeTurtleId to " + activeTurtleId);
		return activeTurtleId;
	}

	// Return a copy, both as a snapshot and for security
	Set<Integer> getToldTurtles() {
		return new LinkedHashSet<>(toldTurtleIds);
	}

	double moveTurtleForward(int index, double pixels) {// throws TurtleOutOfScreenException {
		System.out.println("Moving turtle " + index + " by " + pixels);
		Turtle turtle = getTurtle(index);
		double oldX = turtle.getX();
		double oldY = turtle.getY();
		turtle.moveForward(pixels);
		double turtleX = turtle.getX();
		double turtleY = turtle.getY();
		double absDistanceMoved = Math.abs(pixels);
		if (crossesBounds(turtleX, turtleY)) {
			absDistanceMoved = Math.abs(handleTurtleWrapping(index, oldX, oldY));
		} else {
			turtleView.move(getZeroBasedId(index), turtleX, turtleY);			
		}
		// Update front end
		System.out.println("New x: " + turtleX + "; New y: " + turtleY);
		if (absDistanceMoved < Math.abs(pixels)) {
			moveTurtleForward(index, pixels > 0 ? pixels - absDistanceMoved : pixels + absDistanceMoved);
		}
		return pixels;
	}

	// NOTE : Made public to support Controller
	public double moveCurrentTurtlesForward(double pixels)  {
		return doForToldTurtles(turtleId -> moveTurtleForward(turtleId, pixels));
	}

	double rotateTurtle(int index, boolean clockwise, double angleInDegrees) {
		Turtle turtle = getTurtle(index);
		turtle.rotate(clockwise, angleInDegrees);
		System.out.println("New angle: " + turtle.getAngleInDegrees());
		// Update front end
		turtleView.rotate(getZeroBasedId(index), turtle.getAngleInDegrees());
		return angleInDegrees;
	}

	// NOTE : Made public to support Controller
	public double rotateCurrentTurtles(boolean clockwise, double angleInDegrees) {
		return doForToldTurtles(turtleId -> rotateTurtle(turtleId, clockwise, angleInDegrees));
	}

	double setHeading(int index, double angleInDegrees) {
		Turtle turtle = getTurtle(index);
		// Update front end
		double angleRotated = turtle.setAngle(angleInDegrees);
		turtleView.rotate(getZeroBasedId(index), turtle.getAngleInDegrees());
		return angleRotated;
	}

	double setCurrentTurtlesHeading(double angleInDegrees) {
		return doForToldTurtles(turtleId -> setHeading(turtleId, angleInDegrees));
	}

	double setTowards(int index, double x, double y) {
		Turtle turtle = getTurtle(index);
		double angleRotated = turtle.setTowards(x, y);
		System.out.println("Angle in degrees: " + turtle.getAngleInDegrees());
		turtleView.rotate(getZeroBasedId(index), turtle.getAngleInDegrees());
		return angleRotated;
	}

	double setTowardsCurrentTurtles(double x, double y) {
		return doForToldTurtles(turtleId -> setTowards(turtleId, x, y));
	}

	double setXY(int index, double x, double y) {
		Turtle turtle = getTurtle(index);
		if (crossesBounds(x, y)) {
			x = wrapX(x, xBounds);
			y = wrapY(y, yBounds);			
		}
		double distanceMoved = turtle.setXY(x, y);
		turtleView.pickUpPen();
		turtleView.move(getZeroBasedId(index), turtle.getX(), turtle.getY());
		if (!turtle.isPenUp()) {
			turtleView.putDownPen();			
		}
		return distanceMoved;
	}

	double setCurrentTurtlesXY(double x, double y) {
		return doForToldTurtles(turtleId -> setXY(turtleId, x, y));
	}

	public double setPenDown(int index) {
		Turtle turtle = getTurtle(index);
		// TODO - should this need index as arguments too?
		turtle.setPenUp(false);
		turtleView.putDownPen();
		return 1;
	}

	double setCurrentTurtlesPenDown() {
		return doForToldTurtles(turtleId -> setPenDown(turtleId));
	}

	public double setPenUp(int index) {
		Turtle turtle = getTurtle(index);
		// TODO - should this need index as arguments too?
		turtle.setPenUp(true);
		turtleView.pickUpPen();
		return 0;
	}

	double setCurrentTurtlesPenUp() {
		return doForToldTurtles(turtleId -> setPenUp(turtleId));
	}

	double showTurtle(int index) {
		return toggleTurtleShow(index, true);
	}

	double showCurrentTurtles() {
		return doForToldTurtles(turtleId -> showTurtle(turtleId));
	}

	double hideTurtle(int index) {
		return toggleTurtleShow(index, false);
	}

	double hideCurrentTurtles() {
		return doForToldTurtles(turtleId -> hideTurtle(turtleId));
	}

	double goHome(int index) {
		return setXY(index, 0, 0);
	}

	double goHomeCurrentTurtles() {
		return doForToldTurtles(turtleId -> goHome(turtleId));
	}

	double xCor(int index) {
		return getTurtle(index).getX();
	}

	double currentTurtlesXCor() {
		return xCor(getQueryTurtleId());
	}

	double yCor(int index) {
		return getTurtle(index).getY();
	}

	double currentTurtlesYCor() {
		return yCor(getQueryTurtleId());
	}

	double heading(int index) {
		return getTurtle(index).getAngleInDegrees();
	}

	double currentTurtlesHeading() {
		return heading(getQueryTurtleId());
	}

	public double isPenDown(int index) {
		return getTurtle(index).isPenUp() ? 0 : 1;
	}

	double isCurrentTurtlesPenDown() {
		return isPenDown(getQueryTurtleId());
	}

	double isShowing(int index) {
		return getTurtle(index).isShowing() ? 1 : 0;
	}

	double isCurrentTurtlesShowing() {
		return isShowing(getQueryTurtleId());
	}

	private double toggleTurtleShow(int index, boolean showing) {
		Turtle turtle = getTurtle(index);
		turtle.setShowing(showing);
		if (showing) {
			turtleView.showTurtle(getZeroBasedId(index));
			return 1;
		} else {
			turtleView.hideTurtle(getZeroBasedId(index));
			return 0;
		}
	}

 
	// If amount moved is returned, can use to keep moving until fully moved
	private double handleTurtleWrapping(int index, double oldX, double oldY) {
		Turtle turtle = getTurtle(index);
		double unwrappedX = turtle.getX();
		double unwrappedY = turtle.getY();
		
		System.out.println("Old X: " + oldX);
		System.out.print("Old Y: " + oldY);

		// First, draw line to edge
		double[] edgeXY = getExceedingEdgeXY(index, unwrappedX, unwrappedY);
		System.out.println("Edge X: " + edgeXY[0]);
		System.out.println("Edge Y: " + edgeXY[1]);
		// Call move to edgeX, edgeY to register that line segment
		turtleView.move(getZeroBasedId(index), edgeXY[0], edgeXY[1]);
		// SetXY to reflection point
		double[] reflectionXY = getReflectionPoint(index, edgeXY[0], edgeXY[1]);
		System.out.println("Reflection X: " + reflectionXY[0]);
		System.out.println("Reflection Y: " + reflectionXY[1]);
		setXY(index, reflectionXY[0], reflectionXY[1]);		
		// Calculate amount moved
		double distanceMovedToEdge = Math.sqrt(Math.pow(edgeXY[0] - oldX, 2) + Math.pow(edgeXY[1] - oldY, 2));
		System.out.println("Distance moved to edge : " + distanceMovedToEdge);
		return distanceMovedToEdge;
	}

	// only called if either X or Y or both are out of bounds
	private double[] getExceedingEdgeXY(int index, double oldX, double oldY) {
		Turtle turtle = getTurtle(index);
		double turtleX = turtle.getX();
		double turtleY = turtle.getY();
		double edgeX = turtleX;
		double edgeY = turtleY;
		if (turtleX < -xBounds || turtleX > xBounds) {
			System.out.println("x exceeded");
			if (turtleX < -xBounds) {
				edgeX = -xBounds;
			}
			if (turtleX > xBounds) {
				edgeX = xBounds;
			}
			edgeY = (edgeX - oldX) * Math.tan(turtle.getAngle()) + oldY;
			if (crossesBounds(edgeX, edgeY)) {
				edgeY = wrapY(edgeY, yBounds);
			}
			System.out.println("EdgeX is " + edgeX + " for turtleX of " + turtleX);
			System.out.println("EdgeY is " + edgeY + " for turtleY of " + turtleY);
		}
		else if (turtleY < -yBounds || turtleY > yBounds) {
			System.out.println("y exceeded");
			if (turtleY < -yBounds) {
				edgeY = -yBounds;
			}
			if (turtleY > yBounds) {
				edgeY = yBounds;
			}
			edgeX = (edgeY - oldY) / Math.tan(turtle.getAngle()) + oldX;
			if (crossesBounds(edgeX, edgeY)) {
				edgeX = wrapX(edgeX, xBounds);
			}
			System.out.println("EdgeX is " + edgeX + " for turtleX of " + turtleX);
			System.out.println("EdgeY is " + edgeY + " for turtleY of " + turtleY);
		}
		return new double[] { edgeX, edgeY };
	}
	
		private double[] getReflectionPoint(int index, double edgeX, double edgeY) {
		double reflectionX = edgeX;
		double reflectionY = edgeY;
		if (edgeX == -xBounds) {
			reflectionX = xBounds;
		}
		if (edgeX == xBounds) {
			reflectionX = -xBounds;
		}
		if (edgeY == -yBounds) {
			reflectionY = yBounds;
		}
		if (edgeY == yBounds) {
			reflectionY = -yBounds;
		}
		System.out.println("Reflection X is " + reflectionX + " for edgeX of " + edgeX);
		System.out.println("Reflection Y is " + reflectionY + " for edgeY of " + edgeY);
		return new double[] { reflectionX, reflectionY };
	}

	private void keepTurtleInBounds(int index) {
		keepTurtleInXBounds(index);
		keepTurtleInYBounds(index);
	}

	private void keepTurtleInXBounds(int index) {
		Turtle turtle = getTurtle(index);
		double turtleX = turtle.getX();
		double newX = turtleX > xBounds  || turtleX < -xBounds ? wrapX(turtleX, xBounds) : turtleX;
		turtle.setXY(newX, turtle.getY());
	}

	private void keepTurtleInYBounds(int index) {
		Turtle turtle = getTurtle(index);
		double turtleY = turtle.getY();
		double newY = turtleY > yBounds || turtleY < -yBounds  ? wrapY(turtleY, yBounds) : turtleY;
		turtle.setXY(turtle.getX(), newY);
	}
	
	private boolean crossesBounds(double turtleX, double turtleY) {
		if (turtleX < -xBounds || turtleX > xBounds || turtleY < -yBounds || turtleY > yBounds) {
			return true;
		}
		return false;
	}

	private int getZeroBasedId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException();
		}
		return id - 1;
	}

	private double wrapX(double xCoords, double xBounds) {
		//double wrappedX = Math.floorMod((int) (xCoords + xBounds), (int) (2 * xBounds)) - xBounds;
		double wrappedX;
		double modNumerator = xCoords + xBounds;
		double modDenominator = 2 * xBounds;
		if (modNumerator < 0) {
			wrappedX = modDenominator - (-modNumerator % modDenominator) - xBounds;
		} else {
			wrappedX = (modNumerator % modDenominator) - xBounds;
		}
		System.out.println("wrappedX for " + xCoords + " : " + wrappedX);
		return wrappedX;
	}

	private double wrapY(double yCoords, double yBounds) {
		//double wrappedY = Math.floorMod((int) (yCoords + yBounds), (int) (2 * yBounds)) - yBounds;
		double wrappedY;
		double modNumerator = yCoords + yBounds;
		double modDenominator = 2 * yBounds;
		if (modNumerator < 0) {
			wrappedY = modDenominator - (-modNumerator % modDenominator) - yBounds;
		} else {
			wrappedY = (modNumerator % modDenominator) - yBounds;
		}
		System.out.println("wrappedY for " + yCoords + " : " + wrappedY);
		return wrappedY;
	}

}
