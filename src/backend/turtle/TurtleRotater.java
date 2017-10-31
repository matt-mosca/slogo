package backend.turtle;

import frontend.turtle_display.TurtleView;

import java.util.List;

/**
 * Handles rotations of the backend representations of turtles and instructs the frontend to change to match the
 * backend representations' updates states.
 *
 * @author Adithya Raghunathan
 * @author Ben Schwennesen
 */
public class TurtleRotater {

    private TurtleView turtleView;
    private List<Turtle> turtles;

    public TurtleRotater(TurtleView turtleView, List<Turtle> turtles) {
        this.turtleView = turtleView;
        this.turtles = turtles;
    }

    double rotateTurtle(int index, boolean clockwise, double angleInDegrees) {
        Turtle turtle = turtles.get(index);
        turtle.rotate(clockwise, angleInDegrees);
        // Update front end
        turtleView.rotate(index, turtle.getAngleInDegrees());
        return angleInDegrees;
    }

    double setHeading(int index, double angleInDegrees) {
        Turtle turtle = turtles.get(index);
        // Update front end
        double angleRotated = turtle.setAngle(angleInDegrees);
        turtleView.rotate(index, turtle.getAngleInDegrees());
        return angleRotated;
    }

    double setTowards(int index, double x, double y) {
        Turtle turtle = turtles.get(index);
        double angleRotated = turtle.setTowards(x, y);
        turtleView.rotate(index, turtle.getAngleInDegrees());
        return angleRotated;
    }
}
