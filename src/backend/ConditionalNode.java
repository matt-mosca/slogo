package backend;

import java.lang.reflect.InvocationTargetException;

import commands.AbstractCommand;
import commands.ControlCommand;

public abstract class ConditionalNode implements SyntaxNode {


	
	// TODO
	@Override
	public double execute() throws IllegalAccessException, InvocationTargetException {
		return 0.0;
	}

}
