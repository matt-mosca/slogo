package backend.control_nodes;

import backend.FunctionsStore;
import backend.SyntaxNode;

public abstract class DataAccessingNode implements SyntaxNode {

    private FunctionsStore store;

    public DataAccessingNode(FunctionsStore store) {
        this.store = store;
    }

    protected double getVariableValue(String name) {
        return store.getVariableValue(name);
    }

    protected double setVariable(String name, Double value) {
        store.setVariable(name, value);
        return value;
    }
}
