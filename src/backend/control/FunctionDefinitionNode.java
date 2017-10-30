package backend.control;

import backend.SyntaxNode;

import java.util.List;

/**
 * @author Ben Schwennesen
 */
public class FunctionDefinitionNode extends ControlNode {

    private SyntaxNode functionRoot;
    private List<String> parameterNames;

    private final String FUNCTION_NAME;

    public FunctionDefinitionNode(String commandName, ScopedStorage store, String functionName,
                                  SyntaxNode functionRoot, List<String> parameterNames) {
        super(commandName, store);
        this.functionRoot = functionRoot;
        this.parameterNames = parameterNames;
        FUNCTION_NAME = functionName;
    }

    @Override
    public double execute() {
        getStore().addFunctionParameterNames(FUNCTION_NAME, parameterNames);
        return getStore().addFunction(FUNCTION_NAME, functionRoot);
    }

	@Override
	public String serialize() {
		return null;
	}
	
	public SyntaxNode getFunctionRoot() {
		return functionRoot;
	}
}
