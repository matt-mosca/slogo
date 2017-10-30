package backend.control;

import backend.SyntaxNode;

import java.util.List;

/**
 * @author Ben Schwennesen
 */
public class FunctionDefinitionNode extends ControlNode {

    private SyntaxNode functionRoot;

    private final String FUNCTION_NAME;

    public FunctionDefinitionNode(String commandName, ScopedStorage store, String functionName, SyntaxNode functionRoot) {
        super(commandName, store);
        this.functionRoot = functionRoot;
        //this.parameterNames = parameterNames;
        FUNCTION_NAME = functionName;
    }

    @Override
    public double execute() {
        return getStore().addFunction(FUNCTION_NAME, functionRoot);
    }

	@Override
	public String serialize() {
		return null;
	}
}
