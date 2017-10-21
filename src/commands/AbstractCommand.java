package commands;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractCommand {

    public abstract double execute() throws IllegalAccessException, InvocationTargetException;

}
