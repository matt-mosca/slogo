package backend;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MathCommand extends Command {

	static enum MathCommandType {
		// TODO - fill in all
		sum, sin, pi;
	}

	private MathCommandType mathCommandType;

	MathCommand(String commandName) throws IllegalArgumentException {
		super(CommandType.math, commandName);
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
	double evaluate(List<Double> operands) {
		// TODO - call private helper functions for SUM, DIFFERENCE, etc.
		switch (mathCommandType) {
		case sum:
			return evaluateSum(operands);
		case sin:
			return evaluateSin(operands);
		case pi:
			return Math.PI;
		}

		return 0; // TEMP
	}

	private double evaluateSum(List<Double> operands) {
		System.out.println("Evaluating SUM");
		double lValue = operands.get(0);
		double rValue = operands.get(1);
		return lValue + rValue;
	}

	private double evaluateSin(List<Double> operands) {
		System.out.println("Evaluating SIN");
		return Math.sin(operands.get(0));
	}
}
