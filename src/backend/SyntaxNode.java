package backend;

import backend.error_handling.SLogoException;

public interface SyntaxNode {
	
	public abstract double execute() throws SLogoException;
	
	public abstract String serialize();

}
