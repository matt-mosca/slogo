package deprecated_commands;

import java.lang.reflect.InvocationTargetException;

/**
 * @deprecated 
 */
public abstract class AbstractCommand {

    public abstract double execute() throws IllegalAccessException, InvocationTargetException;

}
