package backend.turtle;

import frontend.turtle_display.TurtleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntToDoubleFunction;

public class TurtleController {

	private TurtleView turtleView;
	private double xBounds;
	private double yBounds;

	private List<Turtle> createdTurtles;

	private TurtleMover turtleMover;
	private TurtleRotater turtleRotater;

	private int activeTurtleId;
	private int queryTurtleId; // Used when turtle to be queried is different from active turtle
	// For O(1) checking of duplicates while preserving insertion order
	private LinkedHashSet<Integer> toldTurtleIds;

	public TurtleController(TurtleView turtleView, double xBounds, double yBounds) {
		this.turtleView = turtleView;
		this.xBounds = xBounds;
		this.yBounds = yBounds;
		createdTurtles = new ArrayList<>();
		Turtle firstTurtle = new Turtle();
		activeTurtleId = 1;
		queryTurtleId = 0;
		createdTurtles.add(firstTurtle);
		toldTurtleIds = new LinkedHashSet<Integer>(Arrays.asList(new Integer[] { activeTurtleId }));
		turtleMover = new TurtleMover(turtleView, createdTurtles, xBounds, yBounds);
		turtleRotater = new TurtleRotater(turtleView, createdTurtles);
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

	public double setActiveTurtles(Integer[] ids) {
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

	// NOTE : Made public to support Controller
	public double moveCurrentTurtlesForward(double pixels)  {
		return doForToldTurtles(turtleId -> {
			addTurtles(turtleId);
			return turtleMover.moveTurtleForward(getZeroBasedId(turtleId), pixels);
		});
	}

	// NOTE : Made public to support Controller
	public double rotateCurrentTurtles(boolean clockwise, double angleInDegrees) {
		return doForToldTurtles(turtleId ->
				turtleRotater.rotateTurtle(getZeroBasedId(turtleId), clockwise, angleInDegrees));
	}



	double setCurrentTurtlesHeading(double angleInDegrees) {
		return doForToldTurtles(turtleId ->
				turtleRotater.setHeading(getZeroBasedId(turtleId), angleInDegrees));
	}



	double setTowardsCurrentTurtles(double x, double y) {
		return doForToldTurtles(turtleId ->
				turtleRotater.setTowards(getZeroBasedId(turtleId), x, y));
	}

	double setCurrentTurtlesXY(double x, double y) {
		return doForToldTurtles(turtleId -> turtleMover.setXY(getZeroBasedId(turtleId), x, y));
	}

	public double setPenDown(int index) {
		Turtle turtle = getTurtle(index);
		// TODO - should this need index as arguments too?
		turtle.setPenUp(false);
		turtleView.putDownPen(index);
		return 1;
	}

	double setCurrentTurtlesPenDown() {
		return doForToldTurtles(turtleId -> setPenDown(turtleId));
	}

	public double setPenUp(int index) {
		Turtle turtle = getTurtle(index);
		// TODO - should this need index as arguments too?
		turtle.setPenUp(true);
		turtleView.pickUpPen(index);
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
		return turtleMover.setXY(getZeroBasedId(index), 0, 0);
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

	private int getZeroBasedId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException();
		}
		return id - 1;
	}

	public List<Integer> getToldTurtleIds() { return new ArrayList<>(toldTurtleIds); }
}
