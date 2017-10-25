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
        activeTurtleId = 0;
        createdTurtles.add(firstTurtle);
    }

    // TELL [ 100 ] -- creates all turtles up to 100
    void addTurtles(int turtleId) {
        for (int newTurtleId = createdTurtles.size(); newTurtleId <= turtleId; newTurtleId++) {
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
    Turtle getTurtle(int id) {
        if (id > getNumberTurtlesCreated()) {
            addTurtles(id);
        }
        return createdTurtles.get(id);
    }

    Turtle getActiveTurtle() {
        return getTurtle(activeTurtleId);
    }

    int getActiveTurtleId() { return activeTurtleId; }

    void setActiveTurtle(int id) {
        activeTurtleId = id;
    }
    
    double moveTurtleForward(int index, double pixels) {
    		System.out.println("Moving turtle " + index + " by " + pixels);
    		Turtle turtle = getTurtle(index);
    		turtle.moveForward(pixels);
    		// Update front end
    		System.out.print("New x: " + turtle.getX() + "; New y: " + turtle.getY());
    		turtleView.move(index, turtle.getX(), turtle.getY());
    		return pixels;
    }
    
	double moveCurrentTurtleForward(double pixels) {
		return moveTurtleForward(activeTurtleId, pixels);
	}
	
	double rotateTurtle(int index, boolean clockwise, double angleInDegrees) {
		Turtle turtle = getTurtle(index);
		turtle.rotate(clockwise, angleInDegrees);
		System.out.println("New angle: " + turtle.getAngle());
		// Update front end
		turtleView.rotate(index, turtle.getAngle());
		return angleInDegrees;
	}
	
	double rotateCurrentTurtle(boolean clockwise, double angleInDegrees) {
		return rotateTurtle(activeTurtleId, clockwise, angleInDegrees);
	}
	
	double setHeading(int index, double angleInDegrees) {
		Turtle turtle = getTurtle(index);
		// Update front end
		double angleRotated = turtle.setAngle(angleInDegrees);
		turtleView.rotate(index, turtle.getAngle());
		return angleRotated;
	}
	
	double setCurrentTurtleHeading(double angleInDegrees) {
		return setHeading(activeTurtleId, angleInDegrees);
	}
	
	double setTowards(int index, double x, double y) {
		Turtle turtle = getTurtle(index);
		double angleRotated = turtle.setTowards(x, y);
		turtleView.rotate(index, turtle.getAngle());
		return angleRotated;
	}
	
	double setTowardsCurrentTurtle(double x, double y) {
		return setTowards(activeTurtleId, x, y);
	}
	
	double setXY(int index, double x, double y) {
		Turtle turtle = getTurtle(index);
		double distanceMoved = turtle.setXY(x, y);
		turtleView.move(index, turtle.getX(), turtle.getY());
		return distanceMoved;
	}
	
	double setCurrentTurtleXY(double x, double y) {
		return setXY(activeTurtleId, x, y);
	}
    
	double setPenDown(int index) {
		Turtle turtle = getTurtle(index);
		turtle.setPenUp(false);
		turtleView.putDownPen();
		return 1;
	}
	
	double setCurrentTurtlePenDown() {
		return setPenDown(activeTurtleId);
	}
	
	double setPenUp(int index) {
		Turtle turtle = getTurtle(index);
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
	
	private double toggleTurtleShow(int index, boolean showing) {
		Turtle turtle = getTurtle(index);
		turtle.setShowing(showing);
		if (showing) {
			turtleView.showTurtle(index);
			return 1;
		} else {
			turtleView.hideTurtle(index);
			return 0;
		}
	}
	
    // ASKWITH [ condition ] -- handled in turtle nodes
}
