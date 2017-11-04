package backend.control;

import backend.SyntaxNode;

/**
 * Supplies access to variables, functions, and scoping for control structures and user-defined command nodes.
 *
 * @author Ben Schwennesen
 */
public abstract class ControlNode implements SyntaxNode {

    private ScopedStorage store;

    /**
     * Construct a control node with access to scoped storage (functions, variables).
     *
     * @param store - the object used to store the current workspace's functions and variables
     */
    protected ControlNode(ScopedStorage store) {
        this.store = store;
    }

    /**
     * Give extending classes access function/variable storage.
     *
     * @return the object used to store the current workspace's functions and variables
     */
    protected ScopedStorage getStore() { return store; }
        
    @Override
    public boolean canTakeVariableNumberOfArguments() {
    		return false;
    }
}
