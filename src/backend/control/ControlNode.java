package backend.control;

import backend.SyntaxNode;

/**
 * Supplies access to variables, functions, and scoping for control structures and user-defined command nodes.
 *
 * @author Ben Schwennesen
 */
public abstract class ControlNode implements SyntaxNode {

    private ScopedStorage store;

    protected ControlNode(ScopedStorage store) {
        this.store = store;
    }
        
    protected ScopedStorage getStore() { return store; }
        
    @Override
    public boolean canTakeVariableNumberOfArguments() {
    		return false;
    }

}
