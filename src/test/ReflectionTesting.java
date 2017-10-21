package test;

import commands.AbstractCommand;
import utilities.CommandGetter;

import java.lang.reflect.Method;

public class ReflectionTesting {

    private static final Class[] CONSTRUCTOR_PARAMETER_CLASSES = new Class[]{Method.class};

    public static void main(String[] args) {
        ReflectionTesting t = new ReflectionTesting();
        CommandGetter getter = new CommandGetter();
        double result = Double.MIN_VALUE;
        try {
            AbstractCommand command = getter.getCommandFromName("make".toLowerCase());
            // result = command.execute(new FunctionsStore(), "global", new String[]{"a","b"}, new double[]{1.1,2.3});
        } catch (Exception e) {
            e.printStackTrace(); // for testing only!!!
        }
    }
}
