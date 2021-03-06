package backend.control;

import backend.SyntaxNode;
import backend.error_handling.BadNumberOfArgumentsException;
import backend.error_handling.SLogoException;

import java.util.List;

/**
 * Syntax node for executing user-defined functions.
 *
 * @author Ben Schwennesen
 */
public class FunctionNode extends ControlNode {

    private List<SyntaxNode> parameters;
    private final String FUNCTION_NAME;

    /**
     * Construct a user-defined function execution node.
     *
     * @param store - the object used to store the current workspace's functions and variables
     * @param functionName - the name of the function to execute
     * @param parameters - the expressions to evaluate and set the functions' parameters equal to
     */
    public FunctionNode(ScopedStorage store, String functionName, List<SyntaxNode> parameters) {
        super(store);
        this.parameters = parameters;
        FUNCTION_NAME = functionName;
    }

    @Override
    public double execute() throws SLogoException {
        ScopedStorage store = getStore();
        store.enterScope(FUNCTION_NAME);
        List<String> parameterNames = store.getFunctionParameterNames(FUNCTION_NAME);
        if (parameterNames.size() != parameters.size()) {
            throw new BadNumberOfArgumentsException(FUNCTION_NAME, parameterNames.size());
        }
        for (int parameter = 0; parameter < parameters.size(); parameter++) {
            String parameterName = parameterNames.get(parameter);
            double valueOfParameter = parameters.get(parameter).execute();
            // do not overwrite existing variable with the same name as the parameters
            store.setVariableInScope(FUNCTION_NAME, parameterName, valueOfParameter);
        }
        double result = store.getFunctionRoot(FUNCTION_NAME).execute();
        store.exitScope();
        return result;
    }
	
}
