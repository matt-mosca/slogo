package backend;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MathCommand extends Command {

	static enum MathCommandType {
		// TODO - fill in all
		SUM, SIN, PI;
	}
	
	private MathCommandType mathCommandType;

	public MathCommand(String commandName) throws IllegalArgumentException {
		super(CommandType.MATH, commandName);
		// Verify it is a valid name
		mathCommandType = MathCommandType.valueOf(commandName);
	}

	public static Collection<String> getCommands() {
		Set<String> commandStrings = new HashSet<>();
		for (MathCommandType commandType : MathCommandType.class.getEnumConstants()) {
			commandStrings.add(commandType.toString());
		}
		return commandStrings;
	}

	@Override
	public SyntaxNodeType getSyntaxNodeType() {
		// TODO - update once MathCommandType enum is updated
		switch (mathCommandType) {
			case SUM :
				return SyntaxNodeType.BINARY_INTERIOR;
			case SIN :
				return SyntaxNodeType.UNARY_INTERIOR;
			case PI :
				return SyntaxNodeType.TERMINAL;
		}
		// Shouldn't happen
		return SyntaxNodeType.TERMINAL;
	}
	
	@Override
	public double execute(double leftVal, double rightVal) {
		// TODO - call private helper functions for SUM, DIFFERENCE, etc.
		return 0; // TEMP
	}
}
