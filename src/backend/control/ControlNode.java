package backend.control;

import backend.SyntaxNode;

/**
 * @author Ben Schwennesen
 */
public abstract class ControlNode implements SyntaxNode {

    private ScopedStorage store;
    private String commandName;
    private String serializedString;

    public ControlNode(String commandName, ScopedStorage store) {
    		this.commandName = commandName;    	
    		this.serializedString = commandName;
        this.store = store;
    }
        
    protected ScopedStorage getStore() { return store; }
    
    protected String getCommandName() {
    		return commandName;
    }
        
    protected String getSerializedString() {
    		return serializedString;
    }
    
    protected void setSerializedString(String newString) {
    		serializedString = newString;
    }

}
