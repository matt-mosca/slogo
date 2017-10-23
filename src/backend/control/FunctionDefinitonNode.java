package backend.control;

import backend.SyntaxNode;

/**
 * @author Ben Schwennesen
 */
public class FunctionDefinitonNode extends DataAccessingNode {

    private SyntaxNode functionRoot;
    private final String FUNCTION_NAME;

    public FunctionDefinitonNode(ScopedStorage store, String functionName, SyntaxNode functionRoot) {
        super(store);
        FUNCTION_NAME = functionName;
        this.functionRoot = functionRoot;
    }

    @Override
    public double execute() {
        return getStore().addFunction(FUNCTION_NAME, functionRoot);
    }
}
