package backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.CommandGetter;

public class Parser {

	public static final String DELIMITER_REGEX = "\\s+";
	public static final String STANDARD_DELIMITER = " ";
	public static final String NUMBER_REGEX = "-?[0-9]+\\.?[0-9]*";

	private CommandGetter commandGetter;
	private Map<String, CommandType> commandNamesToTypes;
	private Map<String, SyntaxNode> syntaxTrees; // cache of parsed commands

	public Parser() {
		commandGetter = new CommandGetter();
		commandNamesToTypes = new HashMap<>();
		// TODO - same for other CommandTypes (Logic, Turtle, Control)
		for (String commandName : MathCommand.getCommands()) {
			commandNamesToTypes.put(commandName.toLowerCase(), CommandType.math);
		}
		syntaxTrees = new HashMap<>();
	}

	public boolean validateCommand(String command) {
		if (command == null) {
			return false;
		}
		// Avoid repeated computation for just differing whitespace
		String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
		System.out.println(formattedCommand);
		try {
			syntaxTrees.put(formattedCommand, makeExpTree(command.split(DELIMITER_REGEX), 0));
			System.out.println("Saved syntax tree for valid command");
			return true;
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid command!");
			return false;
		}
	}

	public void executeCommand(String command) throws IllegalArgumentException {
		String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
		if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
			syntaxTrees.put(formattedCommand, makeExpTree(command.split(DELIMITER_REGEX), 0));
		}
		SyntaxNode tree = syntaxTrees.get(formattedCommand);
		System.out.println("About to parse syntax tree!");
		parseSyntaxTree(tree);
	}

	// To support switching of language through front end
	public void setLanguage(String language) {
		commandGetter.setLanguage(language);
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
		String[] commandInfo = commandGetter.getCommandInfo(commandName);
		String canonicalCommandName = commandInfo[0];
		int numChildren = Integer.parseInt(commandInfo[1]);
		CommandType commandType = commandNamesToTypes.get(canonicalCommandName);
		SyntaxNode root = new SyntaxNode(Command.makeCommandFromTypeAndName(commandType, canonicalCommandName));
		index++;
		SyntaxNode nextChild;
		for (int child = 0; child < numChildren; child++) {
			nextChild = makeExpTree(commands, index);
			root.addChild(nextChild);
			index += nextChild.getSize();
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
		List<Double> operands = new ArrayList<>();
		for (SyntaxNode child : tree.getChildren()) {
			System.out.println("Parsing child with command " + child.getCommand().getCommandName());	
			operands.add(parseSyntaxTree(child));
		}
		double result = tree.getCommand().evaluate(operands);
		System.out.println("Result: " + result);
		return result;
	}

	private boolean isNumeric(String command) {
		return command != null && command.matches(NUMBER_REGEX);
	}

}
