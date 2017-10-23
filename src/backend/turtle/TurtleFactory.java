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
    		turtleView.move(index, turtle.getX(), turtle.getY());
    		return pixels;
    }
    
	double moveBackward(int index, double pixels) {
		return moveTurtleForward(index, -pixels);
	}
	
	double moveCurrentTurtleForward(double pixels) {
		return moveTurtleForward(activeTurtleId, pixels);
	}
	

    
    // ASKWITH [ condition ] -- handled in turtle nodes
}
