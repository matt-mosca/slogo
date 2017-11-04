package backend.control;

import backend.SyntaxNode;

/**
 * Syntax node used to store user-defined functions (that is, used for the 'to' command).
 *
 * @author Ben Schwennesen
 */
public class FunctionDefinitionNode extends ControlNode {

    private SyntaxNode functionRoot;

    private final String FUNCTION_NAME;

    /**
     * Construct a function definition command node.
     *
     * @param store - the object used to store the current workspace's functions and variables
     * @param functionName - the name of the function to define
     * @param functionRoot - the function subtree to execute when the function is called
     */
    public FunctionDefinitionNode(ScopedStorage store, String functionName, SyntaxNode functionRoot) {
        super(store);
        this.functionRoot = functionRoot;
        FUNCTION_NAME = functionName;
    }

    @Override
    public double execute() {
        return getStore().addFunction(FUNCTION_NAME, functionRoot);
    }
}
