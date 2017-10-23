package backend.control_nodes;

import backend.ScopedStorage;
import backend.SyntaxNode;

/**
 * @author Ben Schwennesen
 */
public class FunctionDefinitionNode extends DataAccessingNode {

    private SyntaxNode functionRoot;
    private final String FUNCTION_NAME;

    public FunctionDefinitionNode(ScopedStorage store, String functionName, SyntaxNode functionRoot) {
        super(store);
        FUNCTION_NAME = functionName;
        this.functionRoot = functionRoot;
    }

    @Override
    public double execute() {
        return getStore().addFunction(FUNCTION_NAME, functionRoot);
    }
}
