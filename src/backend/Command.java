package backend;

public abstract class Command {

	private CommandType commandType;
	private String commandName;

	// TODO - is there a better way of instantiating the right sub-class?
	public static Command makeCommandFromTypeAndName(CommandType commandType, String commandName) {
		switch (commandType) {
			case MATH :
				return new MathCommand(commandName);
			// TODO - other Command sub-classes
			default : // TEMP
				return new MathCommand(commandName);
		}
	}
	
	protected Command(CommandType commandType, String commandName) {
		this.commandType = commandType;
		this.commandName = commandName;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public String getCommandName() {
		return commandName;
	}

	public abstract SyntaxNodeType getSyntaxNodeType();
	
	public abstract double execute(double leftVal, double rightVal);

}
