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
            x = wrapX(x, xBounds);
            y = wrapY(y, yBounds);
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

    double moveTurtleForward(int index, double pixels) {// throws TurtleOutOfScreenException {
        System.out.println("Moving turtle " + index + " by " + pixels);
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
        System.out.println("New x: " + turtleX + "; New y: " + turtleY);
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

        System.out.println("Old X: " + oldX);
        System.out.print("Old Y: " + oldY);

        // First, draw line to edge
        double[] edgeXY = wrapOnce(index, oldX, oldY);
        System.out.println("Edge X: " + edgeXY[0]);
        System.out.println("Edge Y: " + edgeXY[1]);
        // Call move to edgeX, edgeY to register that line segment
        turtleView.move(index, edgeXY[0], edgeXY[1]);
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
    private double[] wrapOnce(int index, double oldX, double oldY) {
        Turtle turtle = turtles.get(index);
        double updatedX = turtle.getX();
        double updatedY = turtle.getY();
        System.out.println("newX: " + updatedX + " newY: " + updatedY);
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
        System.out.println("x exceeded");
        double updatedX = turtle.getX();
        if (updatedX < -xBounds) {
            updatedX = -xBounds;
        } else if (updatedX > xBounds) {
            updatedX = xBounds;
        }
        double updatedY = (updatedX - oldX) * Math.tan(turtle.getAngle()) + oldY;
        if (crossesBounds(updatedX, updatedY)) {
            updatedY = wrapY(updatedY, yBounds);
        }
        System.out.println("EdgeX is " + updatedX + " for turtleX of " + turtle.getX());
        System.out.println("EdgeY is " + updatedY + " for turtleY of " + turtle.getY());
        return new double[] { updatedX, updatedY };
    }

    private double[] handleYExcess(Turtle turtle, double oldX, double oldY) {
        System.out.println("y exceeded");
        double updatedY = turtle.getY();
        if (updatedY < -yBounds) {
            updatedY = -yBounds;
        } else if (updatedY > yBounds) {
            updatedY = yBounds;
        }
        double updatedX = (updatedY - oldY) / Math.tan(turtle.getAngle()) + oldX;
        if (crossesBounds(updatedX, updatedY)) {
            updatedX = wrapX(updatedX, xBounds);
        }
        System.out.println("EdgeX is " + updatedX + " for turtleX of " + turtle.getX());
        System.out.println("EdgeY is " + updatedY + " for turtleY of " + turtle.getY());
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
        System.out.println("Reflection X is " + reflectionX + " for edgeX of " + edgeX);
        System.out.println("Reflection Y is " + reflectionY + " for edgeY of " + edgeY);
        return new double[] { reflectionX, reflectionY };
    }

    private boolean crossesBounds(double turtleX, double turtleY) {
        if (turtleX < -xBounds || turtleX > xBounds || turtleY < -yBounds || turtleY > yBounds) {
            return true;
        }
        return false;
    }


    private double wrapX(double xCoords, double xBounds) {
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
