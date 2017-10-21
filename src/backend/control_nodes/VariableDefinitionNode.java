package backend.control_nodes;

import backend.FunctionsStore;
import backend.SyntaxNode;

public class VariableDefinitionNode extends DataAccessingNode {

    private SyntaxNode expression;
    private final String VARIABLE_NAME;

    public VariableDefinitionNode(FunctionsStore store, String name, SyntaxNode expression) {
        super(store);
        this.expression = expression;
        VARIABLE_NAME = name;
        // store the variable so that the parser knows it's been defined (but not yet set until execution)
        setVariable(VARIABLE_NAME, null);
    }
    @Override
    public double execute() {
        return setVariable(VARIABLE_NAME, expression.execute());
    }

    public static void main(){
        String name = ":a";
        SyntaxNode expression = null;
        FunctionsStore store = new FunctionsStore();
        VariableDefinitionNode vdn = new VariableDefinitionNode(store, name, expression);

    }
}
