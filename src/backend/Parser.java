package backend;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apis.Command;
import utilities.CommandGetter;
import commands.AbstractCommand;
import commands.Constant;
import commands.MathCommand;

public class Parser {

	public static final String DELIMITER_REGEX = "\\s+";
	public static final String STANDARD_DELIMITER = " ";
	public static final String NUMBER_REGEX = "-?[0-9]+\\.?[0-9]*";

	private CommandGetter commandGetter;
	private Map<String, SyntaxNode> syntaxTrees; // cache of parsed commands

	public Parser() {
		commandGetter = new CommandGetter();
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
		} catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException
				| InstantiationException e) {
			return false;
		}
	}

	public void executeCommand(String command) throws IllegalArgumentException {
		try {
			String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER);
			if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
				syntaxTrees.put(formattedCommand, makeExpTree(command.split(DELIMITER_REGEX), 0));
			}
			SyntaxNode tree = syntaxTrees.get(formattedCommand);
			parseSyntaxTree(tree);
		} catch (IllegalAccessException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException
				| InstantiationException e) {
			throw new IllegalArgumentException();
		}
	}

	// To support switching of language through front end
	public void setLanguage(String language) {
		commandGetter.setLanguage(language);
	}

	private SyntaxNode makeExpTree(String[] commands, int index)
			throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException,
			InvocationTargetException, IllegalAccessException {
		if (commands == null) {
			throw new IllegalArgumentException();
		}
		if (index >= commands.length) {
			return null;
		}
		String commandName = commands[index];
		if (isNumeric(commandName)) {
			Constant constant = new Constant(Double.parseDouble(commandName));
			return new SyntaxNode(constant);
		}
		// TODO - Check variable store for user-defined variables first

		// Account for localization
		String[] commandInfo = commandGetter.getCommandInfo(commandName);
		AbstractCommand command = getCommandFromInfo(commandInfo);
		int numChildren = Integer.parseInt(commandInfo[2]);
		SyntaxNode root = new SyntaxNode(command);
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
	private double parseSyntaxTree(SyntaxNode tree) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (tree == null) {
			throw new IllegalArgumentException();
		}
		Double[] operands = new Double[tree.getChildren().size()];
		List<SyntaxNode> children = tree.getChildren();
		for (int childIndex = 0; childIndex < children.size(); childIndex++) {
			SyntaxNode child = children.get(childIndex);
			operands[childIndex] = parseSyntaxTree(child);
		}
		// FOR DEBUGGING, return directly in future without printing
		double result = tree.getCommand().execute(operands);
		return result;
	}

	private boolean isNumeric(String command) {
		return command != null && command.matches(NUMBER_REGEX);
	}

	// Move to either utilities or to AbstractCommand as static method?
	private AbstractCommand getCommandFromInfo(String[] commandInfo) throws ClassNotFoundException,
			NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
		int numChildren = Integer.parseInt(commandInfo[2]);
		Class commandType = Class.forName(commandInfo[0]);
		Class[] commandConstructorParameterClasses = new Class[] { Class.class, String.class, int.class };
		return (AbstractCommand) commandType.getConstructor(commandConstructorParameterClasses).newInstance(commandType,
				commandInfo[1], numChildren);
	}

}
