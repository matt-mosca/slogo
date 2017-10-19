package utilities;

import java.lang.reflect.Method;

public class Reflector {

    public Method getMethodFromCommandInfo(Class commandType, String[] commandInfo)
            throws ClassNotFoundException, NoSuchMethodException {
        Class[] commandParameterClasses;
        if (commandInfo.length  < 3) {
            commandParameterClasses = new Class[]{};
        } else {
            String[] argumentTypeStrings = commandInfo[2].split(",");
            commandParameterClasses = new Class[argumentTypeStrings.length];
            for (int i = 0; i < argumentTypeStrings.length; i++) {
                if (argumentTypeStrings[i].equals("java.lang.Double")) {
                    commandParameterClasses[i] = double.class;
                } else {
                    commandParameterClasses[i] = Class.forName(argumentTypeStrings[i]);
                }
            }
        }
        return commandType.getDeclaredMethod(commandInfo[1], commandParameterClasses);
    }

}
