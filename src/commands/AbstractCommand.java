package commands;

import apis.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Ben Schwennesen
 */
public abstract class AbstractCommand implements Command{

    private final Method METHOD_TO_INVOKE;

    public AbstractCommand(Class commandType, String methodToInvoke, Class[] parameters) throws NoSuchMethodException {
        METHOD_TO_INVOKE = commandType.getDeclaredMethod(methodToInvoke, parameters);
    }

    @Override
    public double execute(Double... arguments) throws IllegalAccessException, InvocationTargetException {
        METHOD_TO_INVOKE.setAccessible(true);
        return (double) METHOD_TO_INVOKE.invoke(this, arguments);
    }
}
