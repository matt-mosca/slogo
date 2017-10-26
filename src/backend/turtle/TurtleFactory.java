package backend.turtle;

import frontend.turtle_display.TurtleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.IntToDoubleFunction;

public class TurtleFactory {

	private TurtleView turtleView;

	private List<Turtle> createdTurtles;

	private int activeTurtleId;
	// For O(1) checking of duplicates while preserving insertion order
	private LinkedHashSet<Integer> toldTurtleIds;

	public TurtleFactory(TurtleView turtleView) {
		this.turtleView = turtleView;
		createdTurtles = new ArrayList<>();
		Turtle firstTurtle = new Turtle();
		activeTurtleId = 1;
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
	double getNumberTurtlesCreated() {
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

	private double doForToldTurtles(IntToDoubleFunction forEachTurtle) {
		double result = 0;
		for (int toldTurtleId : toldTurtleIds) {
			activeTurtleId = toldTurtleId;
			result = forEachTurtle.applyAsDouble(toldTurtleId);
		}
		return result;
	}

	double setActiveTurtles(Integer[] ids) {
		if (ids.length == 0) {
			throw new IllegalArgumentException();
		}
		toldTurtleIds.clear();
		toldTurtleIds.addAll(Arrays.asList(ids));
		for (int turtleId : toldTurtleIds) {
			addTurtles(turtleId);
		}
		activeTurtleId = ids[ids.length - 1];
		return activeTurtleId;
	}

	double moveTurtleForward(int index, double pixels) {
		System.out.println("Moving turtle " + index + " by " + pixels);
		Turtle turtle = getTurtle(index);
		turtle.moveForward(pixels);
		// Update front end
		System.out.print("New x: " + turtle.getX() + "; New y: " + turtle.getY());
		turtleView.move(getZeroBasedId(index), turtle.getX(), turtle.getY());
		return pixels;
	}

	double moveCurrentTurtlesForward(double pixels) {
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

	double rotateCurrentTurtles(boolean clockwise, double angleInDegrees) {
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
		return doForToldTurtles(turtleId -> setHeading(activeTurtleId, angleInDegrees));
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
		double distanceMoved = turtle.setXY(x, y);
		turtleView.move(getZeroBasedId(index), turtle.getX(), turtle.getY());
		return distanceMoved;
	}

	double setCurrentTurtlesXY(double x, double y) {
		return doForToldTurtles(turtleId -> setXY(turtleId, x, y));
	}

	double setPenDown(int index) {
		Turtle turtle = getTurtle(index);
		// TODO - should this need index as arguments too?
		turtle.setPenUp(false);
		turtleView.putDownPen();
		return 1;
	}

	double setCurrentTurtlesPenDown() {
		return doForToldTurtles(turtleId -> setPenDown(turtleId));
	}

	double setPenUp(int index) {
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
		return doForToldTurtles(turtleId -> showTurtle(activeTurtleId));
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
		return doForToldTurtles(turtleId -> xCor(turtleId));
	}

	double yCor(int index) {
		return getTurtle(index).getY();
	}

	double currentTurtlesYCor() {
		return doForToldTurtles(turtleId -> yCor(turtleId));
	}

	double heading(int index) {
		return getTurtle(index).getAngleInDegrees();
	}

	double currentTurtlesHeading() {
		return doForToldTurtles(turtleId -> heading(turtleId));
	}

	double isPenDown(int index) {
		return getTurtle(index).isPenUp() ? 0 : 1;
	}

	double isCurrentTurtlesPenDown() {
		return doForToldTurtles(turtleId -> isPenDown(turtleId));
	}

	double isShowing(int index) {
		return getTurtle(index).isShowing() ? 1 : 0;
	}

	double isCurrentTurtlesShowing() {
		return doForToldTurtles(turtleId -> isShowing(turtleId));
	}

	double getID() {
		return activeTurtleId;
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

	private int getZeroBasedId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException();
		}
		return id - 1;
	}

	// ASKWITH [ condition ] -- handled in turtle nodes
}
