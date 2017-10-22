package backend.control_nodes;

import backend.ScopedStorage;
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
