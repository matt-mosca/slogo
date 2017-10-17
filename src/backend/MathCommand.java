package backend;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MathCommand extends Command {

	static enum MathCommandType {
		// TODO - fill in all
		SUM, SIN, PI;
	}

	private MathCommandType mathCommandType;

	MathCommand(String commandName) throws IllegalArgumentException {
		super(CommandType.MATH, commandName);
		// Verify it is a valid name
		mathCommandType = MathCommandType.valueOf(commandName);
	}

	static Collection<String> getCommands() {
		Set<String> commandStrings = new HashSet<>();
		for (MathCommandType commandType : MathCommandType.class.getEnumConstants()) {
			commandStrings.add(commandType.toString());
		}
		return commandStrings;
	}

	@Override
	int getNumOperands() {
		// TODO - fetch info from Commands.properties
		return 0;// TEMP
	}

	@Override
	double evaluate(List<Double> operands) {
		// TODO - call private helper functions for SUM, DIFFERENCE, etc.
		return 0; // TEMP
	}
}
