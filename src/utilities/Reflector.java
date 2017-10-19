package utilities;

import java.lang.reflect.Method;

public class Reflector {

    private final String DOUBLE_CLASS = "java.lang.Double";
    private final String CLASS_TYPE_DELIMITER = ",";

    public Method getMethodFromCommandInfo(Class commandType, String[] commandInfo)
            throws ClassNotFoundException, NoSuchMethodException {
        Class[] commandParameterClasses;
        if (commandInfo.length < 3) {
            commandParameterClasses = new Class[]{};
        } else {
            String[] argumentTypeStrings = commandInfo[2].split(CLASS_TYPE_DELIMITER);
            commandParameterClasses = getParameterClasses(argumentTypeStrings);
        }
        return commandType.getDeclaredMethod(commandInfo[1], commandParameterClasses);
    }

    private Class[] getParameterClasses(String[] argumentTypeStrings) throws ClassNotFoundException {
        Class[] commandParameterClasses;
        commandParameterClasses = new Class[argumentTypeStrings.length];
        for (int i = 0; i < argumentTypeStrings.length; i++) {
            if (argumentTypeStrings[i].equals(DOUBLE_CLASS)) {
                commandParameterClasses[i] = double.class;
            } else {
                commandParameterClasses[i] = Class.forName(argumentTypeStrings[i]);
            }
        }
        return commandParameterClasses;
    }

}
