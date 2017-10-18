package apis;


import java.lang.reflect.InvocationTargetException;

/**
 * Object used to represent various SLogo commands. Implementing classes will have the valid commands of their types
 * stored as enums or collections and will represent one of these commands at construction time.
 *
 * @author Ben Schwennesen
 */
public interface Command {
    // TODO - DOCUMENT CHANGE OF REMOVING getAllCommands()...
    /**
     * Execute the command the object represents, as passed at construction time.
     */
    double execute(Double... arguments) throws IllegalAccessException, InvocationTargetException;
    // TODO - DOCUMENT THE CHANGE ABOVE
}

