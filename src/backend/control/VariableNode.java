package backend.control;

import backend.NoArgNode;
import backend.error_handling.UndefinedVariableException;

/**
 * Syntax node used to access the value of a stored variable.
 *
 * @author Ben Schwennesen
 */
public class VariableNode extends NoArgNode {

	private ScopedStorage store;
	private String varName;

	/**
	 * Construct a variable access node.
	 *
	 * @param store - the object used to store the current workspace's functions and variables
	 * @param variableName - the name of the variable to access
	 */
	public VariableNode(ScopedStorage store, String variableName) {
		this.store = store;
		this.varName = variableName;
	}

	@Override
	public double executeSelf(double... arguments) {
		try {
			return store.getVariableValue(varName);
		} catch (UndefinedVariableException e) {
			// per project specifications, by default variables are set to zero
			return 0;
		}
	}
	
}
