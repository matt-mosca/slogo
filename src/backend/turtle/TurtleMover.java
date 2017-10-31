package backend.turtle;

import frontend.turtle_display.TurtleView;

import java.util.List;

public class TurtleMover {

    private double xBounds;
    private double yBounds;

    private TurtleView turtleView;
    private List<Turtle> turtles;

    public TurtleMover(TurtleView turtleView, List<Turtle> turtles, double xBounds, double yBounds) {
        this.xBounds = xBounds;
        this.yBounds = yBounds;
        this.turtles = turtles;
        this.turtleView = turtleView;
    }

    double setXY(int index, double x, double y) {
        Turtle turtle = turtles.get(index);
        if (crossesBounds(x, y)) {
            x = wrap(x, xBounds);
            y = wrap(y, yBounds);
        }
        double distanceMoved = turtle.setXY(x, y);
        turtleView.pickUpPen(index);
        // index zero based already
        turtleView.move(index, turtle.getX(), turtle.getY());
        if (!turtle.isPenUp()) {
            turtleView.putDownPen(index);
        }
        return distanceMoved;
    }

    double moveTurtleForward(int index, double pixels) {
        Turtle turtle = turtles.get(index);
        double oldX = turtle.getX();
        double oldY = turtle.getY();
        turtle.moveForward(pixels);
        double turtleX = turtle.getX();
        double turtleY = turtle.getY();
        double absDistanceMoved = Math.abs(pixels);
        if (crossesBounds(turtleX, turtleY)) {
            absDistanceMoved = Math.abs(handleTurtleWrapping(index, oldX, oldY));
        } else {
            turtleView.move(index, turtleX, turtleY);
        }
        // Update front end
        if (absDistanceMoved < Math.abs(pixels)) {
            moveTurtleForward(index, pixels > 0 ? pixels - absDistanceMoved : pixels + absDistanceMoved);
        }
        return pixels;
    }

    // If amount moved is returned, can use to keep moving until fully moved
    private double handleTurtleWrapping(int index, double oldX, double oldY) {
        Turtle turtle = turtles.get(index);
        double unwrappedX = turtle.getX();
        double unwrappedY = turtle.getY();

        // First, draw line to edge
        double[] edgeXY = wrapOnce(index, oldX, oldY);
        // Call move to edgeX, edgeY to register that line segment
        turtleView.move(index, edgeXY[0], edgeXY[1]);
        // SetXY to reflection point
        double[] reflectionXY = getReflectionPoint(index, edgeXY[0], edgeXY[1]);
        setXY(index, reflectionXY[0], reflectionXY[1]);
        // Calculate amount moved
        double distanceMovedToEdge = Math.sqrt(Math.pow(edgeXY[0] - oldX, 2) + Math.pow(edgeXY[1] - oldY, 2));
        return distanceMovedToEdge;
    }

    // only called if either X or Y or both are out of bounds
    private double[] wrapOnce(int index, double oldX, double oldY) {
        Turtle turtle = turtles.get(index);
        double updatedX = turtle.getX();
        double updatedY = turtle.getY();
        if ((updatedX < -xBounds || updatedX > xBounds) && (updatedY < -yBounds || updatedY > yBounds)) {
            // need to figure out which direction "leaves" first, so figure out if Y was in bounds when X crossed
            return handleXAndYExcess(turtle, oldX, oldY);
        }
        else if (updatedX < -xBounds || updatedX > xBounds) {
            return handleXExcess(turtle, oldX, oldY);
        }
        else if (updatedY < -yBounds || updatedY > yBounds) {
            return handleYExcess(turtle, oldX, oldY);
        } else {
            // finished wrapping
            return new double[] { updatedX, updatedY };
        }
    }

    private double[] handleXAndYExcess(Turtle turtle, double oldX, double oldY) {
        double updatedX = turtle.getX(), updatedY = turtle.getY();
        double excessX = updatedX - (updatedX < -xBounds ? -xBounds : xBounds);
        double slope = (updatedY - oldY) / (updatedX - oldX);
        double yWhenXCrossed = updatedY - excessX * slope;
        if (yWhenXCrossed < -yBounds || yWhenXCrossed > yBounds) {
            // handle as if just y exceeded
            return handleYExcess(turtle, oldX, oldY);
        } else {
            // handle as if just x exceeded
            return handleXExcess(turtle, oldX, oldY);
        }
    }

    private double[] handleXExcess(Turtle turtle, double oldX, double oldY) {
        double updatedX = turtle.getX();
        if (updatedX < -xBounds) {
            updatedX = -xBounds;
        } else if (updatedX > xBounds) {
            updatedX = xBounds;
        }
        double updatedY = (updatedX - oldX) * Math.tan(turtle.getAngle()) + oldY;
        if (crossesBounds(updatedX, updatedY)) {
            updatedY = wrap(updatedY, yBounds);
        }
        return new double[] { updatedX, updatedY };
    }

    private double[] handleYExcess(Turtle turtle, double oldX, double oldY) {
        double updatedY = turtle.getY();
        if (updatedY < -yBounds) {
            updatedY = -yBounds;
        } else if (updatedY > yBounds) {
            updatedY = yBounds;
        }
        double updatedX = (updatedY - oldY) / Math.tan(turtle.getAngle()) + oldX;
        if (crossesBounds(updatedX, updatedY)) {
            updatedX = wrap(updatedX, xBounds);
        }
        return new double[] { updatedX, updatedY };
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
        return new double[] { reflectionX, reflectionY };
    }

    private boolean crossesBounds(double turtleX, double turtleY) {
        if (turtleX < -xBounds || turtleX > xBounds || turtleY < -yBounds || turtleY > yBounds) {
            return true;
        }
        return false;
    }


    private double wrap(double coordinate, double xBounds) {
        double wrappedCoordinate;
        double modNumerator = coordinate + xBounds;
        double modDenominator = 2 * xBounds;
        if (modNumerator < 0) {
            wrappedCoordinate = modDenominator - (-modNumerator % modDenominator) - xBounds;
        } else {
            wrappedCoordinate = (modNumerator % modDenominator) - xBounds;
        }
        return wrappedCoordinate;
    }

    /*private double wrapY(double yCoords, double yBounds) {
        double wrappedY;
        double modNumerator = yCoords + yBounds;
        double modDenominator = 2 * yBounds;
        if (modNumerator < 0) {
            wrappedY = modDenominator - (-modNumerator % modDenominator) - yBounds;
        } else {
            wrappedY = (modNumerator % modDenominator) - yBounds;
        }
        return wrappedY;
    }*/

}
