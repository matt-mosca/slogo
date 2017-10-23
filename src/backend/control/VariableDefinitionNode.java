package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class VariableDefinitionNode extends ControlNode {

    private SyntaxNode expression;
    private final String VARIABLE_NAME;

    public VariableDefinitionNode(ScopedStorage store, String name, SyntaxNode expression) {
        super(store);
        this.expression = expression;
        VARIABLE_NAME = name;
        // store the variable so that the parser knows it's been defined (but not yet set until execution)
        if (!getStore().existsVariable(VARIABLE_NAME)) {
            getStore().setVariable(VARIABLE_NAME, 0.0);
        }
    }

    @Override
    public double execute() throws SLogoException {
        return getStore().setVariable(VARIABLE_NAME, expression.execute());
    }
}