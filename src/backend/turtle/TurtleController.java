package backend.turtle;

import frontend.turtle_display.TurtleView;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Top-level entity for handling turtle-related backend operations, both from
 * commands and direct front end requests (event-handlers from mouse clicks,
 * button presses, etc)
 * 
 * @author Ben Schwennesen
 * @author Adithya Raghunathan
 */

public class TurtleController {

	private TurtleView turtleView;

	private List<Turtle> createdTurtles;

	private TurtleMover turtleMover;
	private TurtleRotater turtleRotater;
	private static final int FIRST_TURTLE_ID = 1;

	private int activeTurtleId;
	private int queryTurtleId; // Used when turtle to be queried is different
								// from active turtle
	// For O(1) checking of duplicates while preserving insertion order
	private LinkedHashSet<Integer> toldTurtleIds;

	/**
	 * Initialize TurtleController with reference to turtleView (class implementing
	 * TurtleDisplay), as well as x and y bounds for turtle
	 * 
	 * @param turtleView
	 * @param xBounds
	 * @param yBounds
	 */
	public TurtleController(TurtleView turtleView, double xBounds, double yBounds) {
		this.turtleView = turtleView;
		createdTurtles = new ArrayList<>();
		Turtle firstTurtle = new Turtle();
		activeTurtleId = FIRST_TURTLE_ID;
		queryTurtleId = 0;
		createdTurtles.add(firstTurtle);
		toldTurtleIds = new LinkedHashSet<Integer>(Arrays.asList(new Integer[] { activeTurtleId }));
		turtleMover = new TurtleMover(turtleView, createdTurtles, xBounds, yBounds);
		turtleRotater = new TurtleRotater(turtleView, createdTurtles);
		initializeFirstTurtle();
	}

	/**
	 * Add as many turtles as needed from current max turtleId to given turtleId
	 * 
	 * @param turtleId
	 *            the id of the turtle up to which turtles must be added
	 */
	// TELL [ 100 ] -- creates all turtles up to 100
	public void addTurtles(int turtleId) {
		for (int newTurtleId = createdTurtles.size() + 1; newTurtleId <= turtleId; newTurtleId++) {
			Turtle newTurtle = new Turtle();
			createdTurtles.add(newTurtle);
			turtleView.addTurtle();
			final int TURTLE_INDEX = getZeroBasedId(newTurtleId);
			turtleView.selectTurtleOnClick(TURTLE_INDEX, e -> {
				setActiveTurtles(new Integer[] { TURTLE_INDEX + 1 });
			});

		}
	}

	/**
	 * 
	 * @return total number of turtles created
	 */
	// TURTLES
	public int getNumberTurtlesCreated() {
		return createdTurtles.size();
	}

	/**
	 * Return a list of the currently told turtle ids, distinct from backing store
	 * Defensively Copied
	 * 
	 * @return
	 */
	public List<Integer> getToldTurtleIds() {
		return toldTurtleIds.stream().mapToInt(id -> getZeroBasedId(id)).boxed().collect(Collectors.toList());
	}

	/**
	 * Set the image of the turtle of the given index to the given image
	 * 
	 * @param index
	 * @param image
	 * @return index
	 */
	public double setTurtleImage(int index, Image image) {
		turtleView.changeImage(index, image);
		return index;
	}

	/**
	 * Reset state to starting values
	 */

	// for undo/redo
	public void clear() {
		createdTurtles.clear();
		activeTurtleId = FIRST_TURTLE_ID;
		queryTurtleId = 0;
		createdTurtles.add(new Turtle());
		toldTurtleIds.clear();
		toldTurtleIds.add(activeTurtleId);
		turtleView.clear();
		initializeFirstTurtle();
	}

	/**
	 * Set the active list of turtles to the specified array
	 * 
	 * @param ids
	 *            the new array of active turtles, 1-indexed
	 * @return
	 */
	public double setActiveTurtles(Integer[] ids) {
		toldTurtleIds.clear();
		toldTurtleIds.addAll(Arrays.asList(ids));
		for (int turtleId : toldTurtleIds) {
			addTurtles(turtleId);
		}
		activeTurtleId = ids.length > 0 ? ids[ids.length - 1] : 0;
		// TODO - Inform the front end of the new list of told turtles
		turtleView.changeRepresentationOfActive(getToldTurtleIds());
		return activeTurtleId;
	}

	/**
	 * Set the pen down for the turtle of the specified 1-indexed index
	 * 
	 * @param index
	 * @return 1 since pen is now down
	 */

	public double setPenDown(int index) {
		Turtle turtle = getTurtle(index);
		// TODO - should this need index as arguments too?
		turtle.setPenUp(false);
		turtleView.putDownPen(getZeroBasedId(index));
		return 1;
	}

	/**
	 * Set the pen up for the turtle of the specified 1-indexed index
	 * 
	 * @param index
	 * @return 0 since pen is now up
	 */
	public double setPenUp(int index) {
		Turtle turtle = getTurtle(index);
		// TODO - should this need index as arguments too?
		turtle.setPenUp(true);
		turtleView.pickUpPen(getZeroBasedId(index));
		return 0;
	}

	/**
	 * Move the current set of active turtles forward by the specified number of
	 * pixels
	 * 
	 * @param pixels
	 *            number of pixels to move
	 * @return the specified number of pixels
	 */
	// NOTE : Made public to support Controller
	public double moveCurrentTurtlesForward(double pixels) {
		return doForToldTurtles(turtleId -> {
			addTurtles(turtleId);
			return turtleMover.moveTurtleForward(getZeroBasedId(turtleId), pixels);
		});
	}

	/**
	 * Rotate the current set of active turtles forward by the specified number of
	 * pixels in the specified direction
	 * 
	 * @param clockwise whether the rotation is clockwise or not
	 * @param angleInDegrees the angle to rotate by
	 * @return the specified angle
	 */
	// NOTE : Made public to support Controller
	public double rotateCurrentTurtles(boolean clockwise, double angleInDegrees) {
		return doForToldTurtles(
				turtleId -> turtleRotater.rotateTurtle(getZeroBasedId(turtleId), clockwise, angleInDegrees));
	}

	/**
	 * Query whether the pen of the specified turtle is up or down
	 * @param index 1-indexed id of turtle
	 * @return 1 if pen down, 0 if pen up
	 */
	public double isPenDown(int index) {
		return getTurtle(index).isPenUp() ? 0 : 1;
	}

	double setCurrentTurtlesPenDown() {
		return doForToldTurtles(turtleId -> setPenDown(turtleId));
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

	// Return a copy, both as a snapshot and for security
	Set<Integer> getToldTurtles() {
		return new LinkedHashSet<>(toldTurtleIds);
	}

	double setCurrentTurtlesHeading(double angleInDegrees) {
		return doForToldTurtles(turtleId -> turtleRotater.setHeading(getZeroBasedId(turtleId), angleInDegrees));
	}

	double setTowardsCurrentTurtles(double x, double y) {
		return doForToldTurtles(turtleId -> turtleRotater.setTowards(getZeroBasedId(turtleId), x, y));
	}

	double setCurrentTurtlesXY(double x, double y) {
		return doForToldTurtles(turtleId -> turtleMover.setXY(getZeroBasedId(turtleId), x, y));
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

	double isCurrentTurtlesPenDown() {
		return isPenDown(getQueryTurtleId());
	}

	double isShowing(int index) {
		return getTurtle(index).isShowing() ? 1 : 0;
	}

	double isCurrentTurtlesShowing() {
		return isShowing(getQueryTurtleId());
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

	private void initializeFirstTurtle() {
		turtleView.selectTurtleOnClick(getZeroBasedId(FIRST_TURTLE_ID),
				e -> setActiveTurtles(new Integer[] { FIRST_TURTLE_ID }));
	}

}
