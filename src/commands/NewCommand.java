package commands;

import java.lang.reflect.InvocationTargetException;

public abstract class NewCommand {

    abstract double execute() throws IllegalAccessException, InvocationTargetException;

}
