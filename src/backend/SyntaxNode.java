package backend;

import backend.error_handling.SLogoException;

public interface SyntaxNode {
	
	public double execute() throws SLogoException;
	
	public String serialize();
	
	public boolean canTakeVariableNumberOfArguments();

}
