package backend.control_nodes;

import backend.FunctionsStore;
import backend.SyntaxNode;
import backend.error_handling.UndefinedVariableException;

public abstract class DataAccessingNode implements SyntaxNode {

    private FunctionsStore store;

    public DataAccessingNode(FunctionsStore store) {
        this.store = store;
    }

    protected double getVariableValue(String name) {
    		try {
    	        return store.getVariableValue(name);    			
    		} catch (UndefinedVariableException e) {
    			return 0;
    		}
    }

    protected double setVariable(String name, Double value) {
        store.setVariable(name, value);
        return value;
    }
}
