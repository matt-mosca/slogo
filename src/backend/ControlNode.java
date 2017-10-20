package backend;

import java.lang.reflect.InvocationTargetException;

import commands.AbstractCommand;
import commands.ControlCommand;

public class ControlNode extends SyntaxNode {

	private ControlCommand controlCommand;
	
	protected ControlNode(ControlCommand command) throws InvocationTargetException, IllegalAccessException {
		super(command);
		controlCommand = command;
	}

	@Override
	public double execute() throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		return controlCommand.execute(this); // TEMP;
	}

}
