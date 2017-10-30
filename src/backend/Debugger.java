package backend;

import backend.control.DoTimesNode;
import backend.error_handling.SLogoException;
import utilities.PeekingIterator;

import java.util.Arrays;
import java.util.List;

public class Debugger {

    /*public void executeCommand(String command) throws SLogoException {
        String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER).trim();
        if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
            syntaxTrees.put(formattedCommand, constructSyntaxTree(
                    new PeekingIterator<String>(Arrays.asList(formattedCommand.split(DELIMITER_REGEX)).iterator())));
        }
        SyntaxNode tree = syntaxTrees.get(formattedCommand);
        tree.execute();
    }*/
/*

	// TODO - Move to debugger?
	public String serializeTree(SyntaxNode root) throws SLogoException {
		if (root == null) {
			return "";
		}
		if (isConstantNode(root.getClass())) {
			return Double.toString(((ConstantNode) root).getValue());
		}
		// Dispatch appropriate method
		try {
			Method nextSerializingMethod = commandGetter.getSerializingMethod(root.getClass());
			System.out.println("Next serializing method: " + nextSerializingMethod.getName());
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
        if (!isValueNode(root.getClass())) {
            throw new IllegalArgumentException();
        }
        String rootString = "";
        ValueNode valueNode = (ValueNode) root;
        boolean isTakingUnlimitedParams = false;
        List<SyntaxNode> children = valueNode.getChildren();
        if (!isRootNode(valueNode.getClass())) {
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
	*/
}
