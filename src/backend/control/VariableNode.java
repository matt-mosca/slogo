package backend.control;

import backend.NoArgNode;
import backend.error_handling.UndefinedVariableException;

// TODO - consider sub-classing SyntaxNode directly?
public class VariableNode extends NoArgNode {
	ScopedStorage store;
	String varName;

	public VariableNode(ScopedStorage scopedStorage, String varName) {
		this.store = scopedStorage;
		this.varName = varName;
	}

	public double executeSelf(double... arguments) {
		// Since parser checks in tree construction whether the variable exists
		// the only time the catch block is entered is when the variable is declared
		// but a value has not been assigned
		// such as in MAKE, SET, etc.
		// in such a case, its instantaneous value does not matter
		// since it is about to be overwritten
		try {
			return store.getVariableValue(varName);
		} catch (UndefinedVariableException e) {
			return 0;
		}
	}
	
}
