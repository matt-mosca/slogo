package test;

import apis.Command;
import backend.FunctionsStore;
import utilities.CommandGetter;
import utilities.Reflector;

import java.lang.reflect.Method;

public class ReflectionTesting {

    private static final Class[] CONSTRUCTOR_PARAMETER_CLASSES = new Class[]{Method.class};

    public static void main(String[] args) {
        ReflectionTesting t = new ReflectionTesting();
        CommandGetter getter = new CommandGetter();
        String[] methodInfo = getter.getCommandInfo("make".toLowerCase());
        double result = Double.MIN_VALUE;
        try {
            Class commandType = Class.forName(methodInfo[0]);
            Reflector reflector = new Reflector();
            Method methodToInvoke = reflector.getMethodFromCommandInfo(commandType, methodInfo);
            Command command = (Command) commandType
                    .getConstructor(CONSTRUCTOR_PARAMETER_CLASSES)
                    .newInstance(methodToInvoke);
            result = command.execute(new FunctionsStore(), "global", new String[]{"a","b"}, new double[]{1.1,2.3});
        } catch (Exception e) {
            e.printStackTrace(); // for testing only!!!
        }
        System.out.println(result);
        System.out.println(Command[].class);
    }
}
