package backend.control;

import backend.SyntaxNode;

/**
 * Supplies access to variables, functions, and scoping for control structures and user-defined command nodes.
 *
 * @author Ben Schwennesen
 */
public abstract class ControlNode implements SyntaxNode {

    private ScopedStorage store;
    private String commandName;
    private String serializedString;

    protected ControlNode(String commandName, ScopedStorage store) {
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
    
    @Override
    public boolean canTakeVariableNumberOfArguments() {
    		return false;
    }

}
