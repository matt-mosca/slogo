package commands;

import java.lang.reflect.Method;

import backend.ControlNode;

public abstract class ControlCommand extends AbstractCommand {

    public ControlCommand (Method methodToInvoke) {
        super(methodToInvoke);
    }
        
    public double execute(ControlNode tree) {
    		// TODO
    		return 0.0;
    }
}
