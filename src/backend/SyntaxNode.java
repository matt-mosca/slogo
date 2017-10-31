package backend;

import backend.error_handling.SLogoException;

/**
 * Interface implemented by all SLogo syntax / command nodes
 * 
 * @author radithya
 *
 */

public interface SyntaxNode {

	/**
	 * Execute the tree to return a double value
	 * 
	 * @return double corresponding to value of executed command tree
	 * @throws SLogoException
	 *             if there was an error at any point of tree execution
	 */

	public double execute() throws SLogoException;

	/**
	 * String representation of command subtree based on state of program execution
	 * 
	 * @return
	 */
	public String serialize();

	/**
	 * Whether this syntax / command node supports unlimited (variable no. of)
	 * parameters
	 * 
	 * @return true if command node supports variable no. of arguments, false
	 *         otherwise
	 */
	public boolean canTakeVariableNumberOfArguments();

}
