package apis;

import javafx.geometry.Point2D;

import java.util.EmptyStackException;

/**
 * Stores stacks of entered commands as well as the turtles' position and heading prior to those commands.
 */
public interface HistoryStore {

    /**
     * Add a new command to the command stack. Should be added at the same time as the turtle's position before the
     * command is executed.
     *
     * @param lastCommand - the last entered command after parsing
     */
    void addCommand(String lastCommand); // package private

    /**
     * Add the last position and heading of the turtle before the last executed command.
     *
     * @param lastPosition - the last (x,y) position of the turtle
     * @param lastHeading - the last heading (direction pointed to) of the turtle
     */
    void addTurtlePosition(Point2D lastPosition, double lastHeading); // package private

    /**
     * Get the last executed command from the commands stack.
     *
     * @return the raw string reprentation of the last command
     */
    String getLastCommand() throws EmptyStackException; // package private

    /**
     * Get the last turtle position before the last executed command from the position stack.
     *
     * @return the last (x,y) position of the turtle
     */
    Point2D getLastTurtlePosition() throws EmptyStackException; // package private

    /**
     * Get the last turtle heading before the last executed command from the heading stack.
     *
     * @return the last heading of the turtle
     */
    double getLastTurtleHeading() throws EmptyStackException; // package private
}

