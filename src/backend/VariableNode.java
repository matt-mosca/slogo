package backend;

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
		return functionStore.getVariableValue(varName);
	}
	
}
