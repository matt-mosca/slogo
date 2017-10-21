package backend;

import java.lang.reflect.InvocationTargetException;

public interface SyntaxNode {
	
	public abstract double execute() throws IllegalAccessException, InvocationTargetException;

}
