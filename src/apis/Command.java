package apis;


import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * Object used to represent various SLogo commands. Implementing classes will have the valid commands of their types
 * stored as enums or collections and will represent one of these commands at construction time.
 */
public interface Command {

    /**
     * Retrieve all the valid commands of a category of command.
     *
     * @return a collection of all the valid commands within a command category
     */
    Collection getAllValidCommands();

    /**
     * Execute the command the object represents, as passed at construction time.
     */
    double execute(Double... arguments) throws IllegalAccessException, InvocationTargetException;
    // TODO - DOCUMENT THE CHANGE ABOVE
}

