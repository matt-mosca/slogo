package commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import backend.ControlNode;

// TODO - Should this extend AbstractCommand or not? Doesn't need the execute method, 
// but having it as subclass facilitates construction of ControlNode ...
public abstract class ControlCommand extends AbstractCommand {

	public ControlCommand(Method methodToInvoke) {
		super(methodToInvoke);
	}

	public double execute(ControlNode tree) throws IllegalAccessException, InvocationTargetException {
		// TODO
		return 0.0;
	}
}
