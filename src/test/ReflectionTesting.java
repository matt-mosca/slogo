package test;

import apis.Command;
import utilities.CommandGetter;

import java.lang.reflect.Method;

public class ReflectionTesting {

    public static void main(String[] args) {
        ReflectionTesting t = new ReflectionTesting();
        CommandGetter getter = new CommandGetter();
        String[] methodInfo = getter.getCommandInfo("pi".toLowerCase());
        int numberOfDoubleParameters = Integer.parseInt(methodInfo[1]);
        double result = Double.MIN_VALUE;
        Method method = null;
        try {
            Class commandType = Class.forName(methodInfo[0]);
            Class[] commandConstructorParameterClasses = new Class[]{Class.class, String.class, int.class};
            Command command = (Command) commandType
                    .getConstructor(commandConstructorParameterClasses)
                    .newInstance(commandType, methodInfo[1], numberOfDoubleParameters);
            result = command.execute(0.0);
        } catch (Exception e) {
            e.printStackTrace(); // for testing only!!!
        }
        System.out.println(result);
    }
}
