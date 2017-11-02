package backend.control;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;
import backend.error_handling.UnbalancedMakeException;

/**
 * @author Ben Schwennesen
 */
public class VariableDefinitionNode extends ControlNode {

    private SyntaxNode[] expressions;
    private final String[] names;

    public VariableDefinitionNode(ScopedStorage store, String[] names, SyntaxNode[] expressions) throws SLogoException {
        super(store);
        this.expressions = expressions;
        this.names = names;
        // store the variable so that the parser knows it's been defined (but not yet set until execution)
        if (names.length != expressions.length) {
            throw new UnbalancedMakeException();
        }
    }

    @Override
    public double execute() throws SLogoException {
        for (int i = 0; i < names.length - 1; i++) {
            getStore().setVariable(names[i], expressions[i].execute());
        }
        return getStore().setVariable(names[names.length-1], expressions[names.length-1].execute());
    }
	
	@Override
	public boolean canTakeVariableNumberOfArguments() {
		return true;
	}
}
