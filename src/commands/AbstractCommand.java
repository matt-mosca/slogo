package commands;

import apis.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractCommand implements Command{

    private final Method METHOD_TO_INVOKE;

    private final Class commandType;

    public AbstractCommand(Class commandType, String methodToInvoke, int numberOfDoubleParameters) throws NoSuchMethodException {
        this.commandType = commandType;
        Class[] doubleArguments = new Class[numberOfDoubleParameters];
        Arrays.fill(doubleArguments, double.class);
        METHOD_TO_INVOKE = commandType.getDeclaredMethod(methodToInvoke, doubleArguments);
    }

    @Override
    public Collection getAllValidCommands() {
        return Arrays.asList(commandType.getDeclaredMethods());
    }

    @Override
    public double execute(Double... arguments) throws IllegalAccessException, InvocationTargetException {
        METHOD_TO_INVOKE.setAccessible(true);
        return (double) METHOD_TO_INVOKE.invoke(this, arguments);
    }
}
