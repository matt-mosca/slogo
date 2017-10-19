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
    private Object[] arguments;

    public AbstractCommand(Method methodToInvoke)  {
        METHOD_TO_INVOKE = methodToInvoke;
    }

    /*public void takeArguments(Object... arguments) {
        this.arguments = arguments;
    }*/

    @Override
    public double execute(Object... arguments) throws IllegalAccessException, InvocationTargetException {
        METHOD_TO_INVOKE.setAccessible(true);
        return (double) METHOD_TO_INVOKE.invoke(this, arguments);
    }
}
