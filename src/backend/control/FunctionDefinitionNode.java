package backend.control;

import backend.SyntaxNode;

/**
 * @author Ben Schwennesen
 */
public class FunctionDefinitionNode extends ControlNode {

    private SyntaxNode functionRoot;

    private final String FUNCTION_NAME;

    public FunctionDefinitionNode(ScopedStorage store, String functionName, SyntaxNode functionRoot) {
        super(store);
        this.functionRoot = functionRoot;
        //this.parameterNames = parameterNames;
        FUNCTION_NAME = functionName;
    }

    @Override
    public double execute() {
        return getStore().addFunction(FUNCTION_NAME, functionRoot);
    }
	
	public SyntaxNode getFunctionRoot() {
		return functionRoot;
	}
}
