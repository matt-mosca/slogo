package backend;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Parser {

	public static final String DELIMITER_REGEX = "\\s+";
	public static final String STANDARD_DELIMITER = " ";
	public static final String NUMBER_REGEX = "-?[0-9]+\\.?[0-9]*";

	private Translator translator;
	private Map<String, CommandType> commandNamesToTypes;
	private Map<String, SyntaxNode> syntaxTrees; // cache of parsed commands

	public Parser(Locale locale) {
		translator = new Translator(locale);
		commandNamesToTypes = new HashMap<>();
		// TODO - same for other CommandTypes (Logic, Turtle, Control)
		for (String commandName : MathCommand.getCommands()) {
			commandNamesToTypes.put(commandName, CommandType.MATH);
		}
		syntaxTrees = new HashMap<>();
	}

	public boolean validateCommand(String command) {
		if (command == null) {
			return false;
		}
		// Avoid repeated computation for just differing whitespace
		String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
		try {
			syntaxTrees.put(formattedCommand, makeExpTree(command.split(DELIMITER_REGEX), 0));
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	public void executeCommand(String command) throws IllegalArgumentException {
		String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
		if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
			syntaxTrees.put(formattedCommand, makeExpTree(command.split(DELIMITER_REGEX), 0));
		}
		SyntaxNode tree = syntaxTrees.get(formattedCommand);
		parseSyntaxTree(tree);
	}

	// To support switching of language through front end
	public void changeLocale(Locale locale) {
		translator.loadStringsForLocale(locale);
	}

	private SyntaxNode makeExpTree(String[] commands, int index) throws IllegalArgumentException {
		if (commands == null) {
			throw new IllegalArgumentException();
		}
		if (index >= commands.length) {
			return null;
		}
		String commandName = commands[index];
		if (isNumeric(commandName)) {
			return new SyntaxNode(new Constant(Double.parseDouble(commandName)));
		}
		// TODO - Check variable store for user-defined variables first

		// Account for localization
		String canonicalCommandName = translator.getCanonicalCommandFromLocaleString(commandName);
		CommandType commandType = commandNamesToTypes.get(canonicalCommandName);
		SyntaxNode root = new SyntaxNode(Command.makeCommandFromTypeAndName(commandType, canonicalCommandName));
		SyntaxNodeType nodeType = root.getCommand().getSyntaxNodeType();
		if (nodeType != SyntaxNodeType.TERMINAL) {
			root.setLeft(makeExpTree(commands, ++index));
		}
		if (nodeType == SyntaxNodeType.BINARY_INTERIOR) {
			root.setRight(makeExpTree(commands, index + root.getLeft().getSize()));
		}
		return root;
	}

	// Handled differently based on type of command
	private double parseSyntaxTree(SyntaxNode tree) {
		// Switch to throwing exception to returning 0, to catch malformed trees?
		// Less elegant but easier to debug?
		if (tree == null) {
			return 0;
		}
		double leftVal = parseSyntaxTree(tree.getLeft());
		double rightVal = parseSyntaxTree(tree.getRight());
		return tree.getCommand().evaluate(leftVal, rightVal);
	}

	private boolean isNumeric(String command) {
		return command != null && command.matches(NUMBER_REGEX);
	}

}
