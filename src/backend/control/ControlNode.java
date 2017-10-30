package backend.control;

import backend.SyntaxNode;

/**
 * @author Ben Schwennesen
 */
public abstract class ControlNode implements SyntaxNode {

    private ScopedStorage store;
    private String commandName;

    public ControlNode(String commandName, ScopedStorage store) {
    		this.commandName = commandName;    	
        this.store = store;
    }
        
    protected ScopedStorage getStore() { return store; }

}
