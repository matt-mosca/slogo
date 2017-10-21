package commands;

import java.lang.reflect.Method;

/**
 * @author Ben Schwennesen
 */
public class LogicCommand extends AbstractCommandOld {

	public LogicCommand(Method methodToInvoke) {
		super(methodToInvoke);
	}

	private double less(double... operands) {
		return operands[0] < operands[1] ? 1 : 0;
	}

	private double greater(double... operands) {
		return operands[0] > operands[1] ? 1 : 0;
	}

	private double equal(double... operands) {
		return operands[0] == operands[1] ? 1 : 0;
	}

	private double notEqual(double... operands) {
		return operands[0] != operands[1] ? 1 : 0;
	}

	private double and(double... operands) {
		return operands[0] != 0 && operands[1] != 0 ? 1 : 0;
	}

	private double or(double... operands) {
		return operands[0] != 0 || operands[1] != 0 ? 1 : 0;
	}

	private double not(double... operands) {
		return operands[0] == 0 ? 1 : 0;
	}
}
