package commands;

import apis.Command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class StoreCommand extends AbstractCommand {

    private final String STORE_NAME;

    public StoreCommand (Class thisClass, String methodToInvoke, Class[] parameters, String storeName)
            throws NoSuchMethodException {
        super(thisClass, methodToInvoke, parameters);
        STORE_NAME = storeName;
    }

    @Override
    public double execute(Double... arguments) throws IllegalAccessException, InvocationTargetException {
        return 0.0;
    }

    private void testMethod(String t1, String t2, Double... ds) {

    }

    public static void main(String[] args) {
        try {
            StoreCommand s = new StoreCommand(StoreCommand.class,
                    "testMethod", new Class[]{String.class, String.class, Double[].class}, "");
            for (Method m : s.getClass().getDeclaredMethods()) {
                System.out.println(m.getName());
                for (Class c : m.getParameterTypes()) {
                    System.out.println("\t" + c.getName());
                }
                Class stringArray = Class.forName("[Ljava.lang.String;");
            }

        } catch (Exception e) {
            System.out.println("FUCK YOU BEN");
        }
    }
}
