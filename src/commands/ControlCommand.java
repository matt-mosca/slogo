package commands;

import java.lang.reflect.Method;

public class ControlCommand extends AbstractCommand {

    public ControlCommand (Method methodToInvoke) {
        super(methodToInvoke);
    }
}
