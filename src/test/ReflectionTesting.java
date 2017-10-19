package test;

import apis.Command;
import backend.FunctionsStore;
import utilities.CommandGetter;

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
            Class[] commandParameterClasses;
            if (methodInfo.length  < 3) {
                commandParameterClasses = new Class[]{};
            } else {
                String[] argumentTypeStrings = methodInfo[2].split(",");
                commandParameterClasses = new Class[argumentTypeStrings.length];
                for (int i = 0; i < argumentTypeStrings.length; i++) {
                    if (argumentTypeStrings[i].equals("java.lang.Double")) {
                        commandParameterClasses[i] = double.class;
                    } else {
                        commandParameterClasses[i] = Class.forName(argumentTypeStrings[i]);
                    }
                }
            }
            Method methodToInvoke = commandType.getDeclaredMethod(methodInfo[1], commandParameterClasses);
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
