package backend.control;

import backend.SyntaxNode;

/**
 * @author Ben Schwennesen
 */
public abstract class DataAccessingNode implements SyntaxNode {

    private ScopedStorage store;

    public DataAccessingNode(ScopedStorage store) {
        this.store = store;
    }

    protected ScopedStorage getStore() { return store; }

}
