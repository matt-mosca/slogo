package backend;

public abstract class DataAccessingNode  {

    private FunctionsStore functionsStore;

    public DataAccessingNode(FunctionsStore functionsStore) {
        this.functionsStore = functionsStore;
    }

    protected double getVariableValue(String name) {
        return functionsStore.getVariableValue(name);
    }

    protected void setVariable(String name, double value) {
        functionsStore.setVariable(name, value);
    }
}
