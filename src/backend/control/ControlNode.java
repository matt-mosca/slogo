package backend.control;

import backend.SyntaxNode;

/**
 * @author Ben Schwennesen
 */
public abstract class ControlNode implements SyntaxNode {

    private ScopedStorage store;

    public ControlNode(ScopedStorage store) {
        this.store = store;
    }

    protected ScopedStorage getStore() { return store; }

}
