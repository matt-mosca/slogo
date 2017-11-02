package backend;

import backend.control.DoTimesNode;
import backend.control.FunctionDefinitionNode;
import backend.control.VariableNode;
import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedCommandException;
import backend.math.ConstantNode;
import utilities.CommandGetter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Debugger {

    public static final String DELIMITER_REGEX = "\\s+";
    public static final String NEWLINE_REGEX = "\n";
    public static final char COMMENT = '#';
    public static final String STANDARD_DELIMITER = " ";
    public static final String NUMBER_REGEX = "-?[0-9]+\\.?[0-9]*";
    public static final String VARIABLE_REGEX = ":[a-zA-Z_]+";
    public static final String VARIABLE_ARGS_START_DELIMITER = "(";
    public static final String VARIABLE_ARGS_END_DELIMITER = ")";
    public static final String LIST_START_DELIMITER = "[";
    public static final String LIST_END_DELIMITER = "]";

    private Parser parser;
    private ParserUtils parserUtils;
    private CommandGetter commandGetter;

    public Debugger(Parser parser, ParserUtils parserUtils) {
    		this.parser = parser;
    		this.parserUtils = parserUtils;
    }
    
    public void executeCommand(String command) throws SLogoException {
        String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER).trim();
        if (!parser.getSyntaxTrees().containsKey(formattedCommand)) { // in case method is called without validation
            return;
        }
        SyntaxNode tree = parser.getSyntaxTrees().get(formattedCommand);
        tree.execute();
        commandGetter = new CommandGetter();
    }

	// TODO - Move to debugger?
	public String serializeTree(SyntaxNode root) throws SLogoException {
		if (root == null) {
			return "";
		}
		if (parserUtils.isConstantNode(root.getClass())) {
			return Double.toString(((ConstantNode) root).getValue());
		}
		// Dispatch appropriate method
		try {
			Method nextSerializingMethod = commandGetter.getSerializingMethod(root.getClass());
			return (String) nextSerializingMethod.invoke(this, root);
		} catch (IllegalAccessException | InvocationTargetException badCommand) {
			badCommand.printStackTrace();
			throw new UndefinedCommandException(root.getClass().getName());
		}
	}
    public String serializeValueNode(SyntaxNode root) throws SLogoException {
        if (root == null) {
            return "";
        }
        if (!parserUtils.isValueNode(root.getClass())) {
            throw new IllegalArgumentException();
        }
        String rootString = "";
        ValueNode valueNode = (ValueNode) root;
        boolean isTakingUnlimitedParams = false;
        List<SyntaxNode> children = valueNode.getChildren();
        if (!parserUtils.isRootNode(valueNode.getClass())) {
            // Check for unlimited params - if so, need to add a '(' and ')'
            if (valueNode.canTakeVariableNumberOfArguments()
                    && children.size() != valueNode.getDefaultNumberOfArguments()) {
                isTakingUnlimitedParams = true;
                rootString += VARIABLE_ARGS_START_DELIMITER + STANDARD_DELIMITER;
            }
            rootString += commandGetter.getNameFromCommandClass(root.getClass());
        }
        for (SyntaxNode child : valueNode.getChildren()) {
            rootString += STANDARD_DELIMITER + serializeTree(child);
        }
        if (isTakingUnlimitedParams) {
            rootString += STANDARD_DELIMITER + VARIABLE_ARGS_END_DELIMITER;
        }
        return rootString;
    }

    public String serializeDoTimesNode(SyntaxNode root) throws SLogoException {
        if (root == null) {
            return "";
        }
        if (!(root instanceof DoTimesNode)) {
            // TODO - need custom SLogoException for serialization?
            throw new IllegalArgumentException();
        }
        DoTimesNode doTimesNode = (DoTimesNode) root;
        String rootString = commandGetter.getNameFromCommandClass(root.getClass());
        String iterationVariableString = doTimesNode.getIterationVariable();
        String endExpressionString = serializeTree(doTimesNode.getEndExpression());
        String commandString = serializeTree(doTimesNode.getCommandSubtree());
        return rootString + STANDARD_DELIMITER + LIST_START_DELIMITER + STANDARD_DELIMITER + iterationVariableString
                + STANDARD_DELIMITER + endExpressionString + STANDARD_DELIMITER + LIST_END_DELIMITER
                + STANDARD_DELIMITER + LIST_START_DELIMITER + STANDARD_DELIMITER + commandString + STANDARD_DELIMITER
                + LIST_END_DELIMITER;
    }

}
