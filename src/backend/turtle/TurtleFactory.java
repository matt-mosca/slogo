package backend.turtle;

import frontend.turtle_display.TurtleView;

import java.util.ArrayList;
import java.util.List;

public class TurtleFactory {
	
    private TurtleView turtleView;

    private List<Turtle> createdTurtles;

    private int activeTurtleId;

    public TurtleFactory(TurtleView turtleView) {
        this.turtleView = turtleView;
        createdTurtles = new ArrayList<>();
        Turtle firstTurtle = new Turtle();
        activeTurtleId = 1;
        createdTurtles.add(firstTurtle);
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
    double getNumberTurtlesCreated() { return createdTurtles.size(); }

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

    int getActiveTurtleId() { return activeTurtleId; }

    void setActiveTurtle(int id) {
    		if (id <= 0) {
    			throw new IllegalArgumentException();
    		}
        activeTurtleId = id;
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
    
	double moveCurrentTurtleForward(double pixels) {
		return moveTurtleForward(activeTurtleId, pixels);
	}
	
	double rotateTurtle(int index, boolean clockwise, double angleInDegrees) {
		Turtle turtle = getTurtle(index);
		turtle.rotate(clockwise, angleInDegrees);
		System.out.println("New angle: " + turtle.getAngleInDegrees());
		// Update front end
		turtleView.rotate(getZeroBasedId(index), turtle.getAngleInDegrees());
		return angleInDegrees;
	}
	
	double rotateCurrentTurtle(boolean clockwise, double angleInDegrees) {
		return rotateTurtle(activeTurtleId, clockwise, angleInDegrees);
	}
	
	double setHeading(int index, double angleInDegrees) {
		Turtle turtle = getTurtle(index);
		// Update front end
		double angleRotated = turtle.setAngle(angleInDegrees);
		turtleView.rotate(getZeroBasedId(index), turtle.getAngleInDegrees());
		return angleRotated;
	}
	
	double setCurrentTurtleHeading(double angleInDegrees) {
		return setHeading(activeTurtleId, angleInDegrees);
	}
	
	double setTowards(int index, double x, double y) {
		Turtle turtle = getTurtle(index);
		double angleRotated = turtle.setTowards(x, y);
		System.out.println("Angle in degrees: " + turtle.getAngleInDegrees());
		turtleView.rotate(getZeroBasedId(index), turtle.getAngleInDegrees());
		return angleRotated;
	}
	
	double setTowardsCurrentTurtle(double x, double y) {
		return setTowards(activeTurtleId, x, y);
	}
	
	double setXY(int index, double x, double y) {
		Turtle turtle = getTurtle(index);
		double distanceMoved = turtle.setXY(x, y);
		turtleView.move(getZeroBasedId(index), turtle.getX(), turtle.getY());
		return distanceMoved;
	}
	
	double setCurrentTurtleXY(double x, double y) {
		return setXY(activeTurtleId, x, y);
	}
    
	double setPenDown(int index) {
		Turtle turtle = getTurtle(index);
		// TODO - should this need index as arguments too?
		turtle.setPenUp(false);
		turtleView.putDownPen();
		return 1;
	}
	
	double setCurrentTurtlePenDown() {
		return setPenDown(activeTurtleId);
	}
	
	double setPenUp(int index) {
		Turtle turtle = getTurtle(index);
		// TODO - should this need index as arguments too?
		turtle.setPenUp(true);
		turtleView.pickUpPen();
		return 0;
	}
	
	double setCurrentTurtlePenUp() {
		return setPenUp(activeTurtleId);
	}
	
	double showTurtle(int index) {
		return toggleTurtleShow(index, true);
	}
	
	double showCurrentTurtle() {
		return showTurtle(activeTurtleId);
	}
	
	double hideTurtle(int index) {
		return toggleTurtleShow(index, false);
	}
	
	double hideCurrentTurtle() {
		return hideTurtle(activeTurtleId);
	}
	
	double goHome(int index) {
		return setXY(index, 0, 0);
	}
	
	double goHomeCurrentTurtle() {
		return goHome(activeTurtleId);
	}
	
	double xCor(int index) {
		return getTurtle(index).getX();
	}
	
	double currentTurtleXCor() {
		return xCor(activeTurtleId);
	}
	
	double yCor(int index) {
		return getTurtle(index).getY();
	}
	
	double currentTurtleYCor() {
		return yCor(activeTurtleId);
	}
	
	double heading(int index) {
		return getTurtle(index).getAngleInDegrees();
	}
	
	double currentTurtleHeading() {
		return heading(activeTurtleId);
	}
	
	double isPenDown(int index) {
		return getTurtle(index).isPenUp() ? 0 : 1;
	}
	
	double isCurrentTurtlePenDown() {
		return isPenDown(activeTurtleId);
	}
	
	double isShowing(int index) {
		return getTurtle(index).isShowing() ? 1 : 0;
	}
	
	double isCurrentTurtleShowing() {
		return isShowing(activeTurtleId);
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
