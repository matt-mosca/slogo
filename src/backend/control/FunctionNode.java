package backend.control;

import backend.SyntaxNode;
import backend.error_handling.BadNumberOfArgumentsException;
import backend.error_handling.SLogoException;

import java.util.List;

/**
 * @author Ben Schwennesen
 */
public class FunctionNode extends ControlNode {

    private List<SyntaxNode> parameters;
    private final String FUNCTION_NAME;

    public FunctionNode(String commandName, ScopedStorage store, String functionName, List<SyntaxNode> parameters) {
        super(commandName, store);
        this.parameters = parameters;
        parameters.forEach(e -> System.out.print(e.serialize() + " "));
        FUNCTION_NAME = functionName;
    }

    @Override
    public double execute() throws SLogoException {
        System.out.print("WTF ");
        parameters.forEach(e -> System.out.print(e.serialize() + " "));
        ScopedStorage store = getStore();
        store.enterScope(FUNCTION_NAME);
        List<String> parameterNames = store.getCurrentFunctionParameterNames();
        if (parameterNames.size() != parameters.size()) {
            System.out.println("BAD ARG NUMBER:: " + FUNCTION_NAME + " expected: " + parameterNames.size() + ", actual: " + parameters.size());
            throw new BadNumberOfArgumentsException(FUNCTION_NAME, parameterNames.size());
        }
        for (int parameter = 0; parameter < parameters.size(); parameter++) {
            store.setVariable(parameterNames.get(parameter), parameters.get(parameter).execute());
        }
        double result = store.getCurrentFunctionRoot().execute();
        store.exitScope();
        return result;
    }

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
}
