package backend;

import java.util.ArrayList;
import java.util.List;

import backend.error_handling.IllegalSyntaxException;
import backend.math.ConstantNode;
import backend.turtle.TurtleNode;
import backend.view_manipulation.ViewNode;
import utilities.PeekingIterator;

/**
 * Utility class that stores constants and provides helper methods for parsing
 * 
 * @author Adithya Raghunathan
 */
public class ParserUtils {

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

	public static final String TURTLE_PACKAGE = "backend.turtle";

	boolean isNumeric(String command) {
		return command != null && command.matches(NUMBER_REGEX);
	}

	// TODO - Helper function for all these?
	boolean isTurtleNode(Class nodeClass) {
		return TurtleNode.class.isAssignableFrom(nodeClass);
	}

	boolean isViewNode(Class nodeClass) {
		return ViewNode.class.isAssignableFrom(nodeClass);
	}

	boolean isValueNode(Class nodeClass) {
		return ValueNode.class.isAssignableFrom(nodeClass);
	}

	// Special cases in serialization
	boolean isConstantNode(Class nodeClass) {
		return ConstantNode.class.isAssignableFrom(nodeClass);
	}

	boolean isRootNode(Class nodeClass) {
		return RootNode.class.isAssignableFrom(nodeClass);
	}

	String stripComments(String command) {
		String[] lines = command.split(NEWLINE_REGEX);
		List<String> nonCommentedLines = new ArrayList<>();
		for (String line : lines) {
			String trimmedLine = line.trim();
			if (trimmedLine.length() > 0 && trimmedLine.charAt(0) != COMMENT) {
				nonCommentedLines.add(trimmedLine);
			}
		}
		return String.join(STANDARD_DELIMITER, nonCommentedLines);
	}

	List<String> getVariableNamesFromList(PeekingIterator<String> it) throws IllegalSyntaxException {
		if (!it.hasNext()) {
			throw new IllegalArgumentException();
		}
		String listStartToken = it.next();
		if (!listStartToken.equals(LIST_START_DELIMITER)) {
			throw new IllegalSyntaxException(LIST_START_DELIMITER);
		}
		List<String> variableNames = new ArrayList<>();
		while (it.hasNext() && !it.peek().equals(LIST_END_DELIMITER)) {
			String variableName = it.next();
			if (!variableName.matches(VARIABLE_REGEX)) {
				throw new IllegalSyntaxException(variableName);
			}
			variableNames.add(variableName);
		}
		if (!it.hasNext()) {
			throw new IllegalSyntaxException(LIST_END_DELIMITER);
		}
		// Consume the ']' token
		it.next();
		return variableNames;
	}

}
