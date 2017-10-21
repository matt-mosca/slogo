package commands;

import java.lang.reflect.Method;

// TODO - Should this extend AbstractCommandOld or not? Doesn't need the execute method,
// but having it as subclass facilitates construction of ControlNode ...
public abstract class ControlCommand extends AbstractCommand {

	/*public ControlCommand(Method methodToInvoke) {
		super(methodToInvoke);
	}*/

}
