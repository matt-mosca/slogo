package backend;

import commands.AbstractCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public interface SyntaxNode {
	
	public abstract double execute() throws IllegalAccessException, InvocationTargetException;

}
