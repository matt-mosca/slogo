package backend;

public abstract class Command {

	private CommandType commandType;
	private String commandName;

	// TODO - is there a better way of instantiating the right sub-class?
	static Command makeCommandFromTypeAndName(CommandType commandType, String commandName) {
		switch (commandType) {
		case MATH:
			return new MathCommand(commandName);
		// TODO - other Command sub-classes
		default: // TEMP
			return new MathCommand(commandName);
		}
	}

	protected Command(CommandType commandType, String commandName) {
		this.commandType = commandType;
		this.commandName = commandName;
	}

	CommandType getCommandType() {
		return commandType;
	}

	String getCommandName() {
		return commandName;
	}

	abstract SyntaxNodeType getSyntaxNodeType();

	abstract double evaluate(double leftVal, double rightVal);

}
