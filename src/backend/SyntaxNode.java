package backend;

import backend.error_handling.SLogoException;

/**
 * Interface implemented by all SLogo syntax / command nodes
 * 
 * @author Adithya Raghunathan
 */

public interface SyntaxNode {

	/**
	 * Execute the tree to return a double value
	 * 
	 * @return double corresponding to value of executed command tree
	 * @throws SLogoException
	 *             if there was an error at any point of tree execution
	 */

	double execute() throws SLogoException;

	/**
	 * Whether this syntax / command node supports unlimited (variable no. of)
	 * parameters
	 * 
	 * @return true if command node supports variable no. of arguments, false
	 *         otherwise
	 */
	boolean canTakeVariableNumberOfArguments();

}
