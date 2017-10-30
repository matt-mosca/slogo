package backend.turtle;

import frontend.turtle_display.TurtleView;

import java.util.List;

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
        System.out.println("New angle: " + turtle.getAngleInDegrees());
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
        System.out.println("Angle in degrees: " + turtle.getAngleInDegrees());
        turtleView.rotate(index, turtle.getAngleInDegrees());
        return angleRotated;
    }
}
