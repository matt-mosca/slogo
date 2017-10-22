package backend;

import backend.error_handling.UndefinedVariableException;

// TODO - consider sub-classing SyntaxNode directly?
public class VariableNode extends NoArgNode {
	FunctionsStore functionStore;
	String varName;

	public VariableNode(FunctionsStore functionStore, String varName) {
		this.functionStore = functionStore;
		this.varName = varName;
	}
	
	@Override
	public double executeSelf(double... arguments) {
		// Since parser checks in tree construction whether the variable exists
		// the only time the catch block is entered is when the variable is declared
		// but a value has not been assigned
		// such as in MAKE, SET, etc.
		// in such a case, its instantaneous value does not matter
		// since it is about to be overwritten
		try {
			return functionStore.getVariableValue(varName);			
		} catch (UndefinedVariableException e) {
			return 0;
		}
	}
	
}
