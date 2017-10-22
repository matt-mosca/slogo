package backend;

import backend.error_handling.SLogoException;

// TODO - consider sub-classing SyntaxNode directly?
public class VariableNode extends NoArgNode {
	ScopedStorage functionStore;
	String varName;

	public VariableNode(ScopedStorage functionStore, String varName) {
		this.functionStore = functionStore;
		this.varName = varName;
	}
	
	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return functionStore.getVariableValue(varName);
	}
	
}
