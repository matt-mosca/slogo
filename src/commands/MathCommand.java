package commands;

import java.lang.reflect.Method;
import java.util.Random;

/**
 * @author Ben Schwennesen
 */
public class MathCommand extends AbstractCommand {

	private final Random RANDOM_NUMBER_GENERATOR;

	public MathCommand(Method methodToInvoke) {
		super(methodToInvoke);
		RANDOM_NUMBER_GENERATOR = new Random();
	}

	private double sum(double... operands) {
		double sum = 0.0;
		for (double operand : operands) {
			sum += operand;
		}
		return sum;
	}

	private double difference(double... operands) {
		return operands[0] - operands[1];
	}

	private double product(double... operands) {
		double product = 1.0;
		for (double operand : operands) {
			product *= operand;
		}
		return product;
	}

	private double quotient(double... operands) {
		return operands[0] / operands[1];
	}

	private double remainder(double... operands) {
		return operands[0] % operands[1];
	}

	private double minus(double... operands) {
		return operands[0] - operands[1];
	}

	private double unaryMinus(double... operands) {
		return -operands[0];
	}

	private double random(double... operands) {
		return operands[0] * RANDOM_NUMBER_GENERATOR.nextDouble();
	}

	private double sine(double... operands) {
		return Math.sin(operands[0]);
	}

	private double cosine(double... operands) {
		return Math.cos(operands[0]);
	}

	private double tangent(double... operands) {
		return Math.tan(operands[0]);
	}

	private double arctangent(double... operands) {
		return Math.atan(operands[0]);
	}

	private double power(double... operands) {
		return Math.pow(operands[0], operands[1]);
	}

	private double pi(double... operands) {
		return Math.PI;
	}

}
