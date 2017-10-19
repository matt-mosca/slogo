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

    private double sum (double... operands) {
        double sum = 0.0;
        for (double operand : operands) {
            sum += operand;
        }
        return sum;
    }

    private double difference (double leftOperand, double rightOperand) {
        return leftOperand - rightOperand;
    }

    private double product (double... operands) {
        double product = 1.0;
        for (double operand : operands) {
            product *= operand;
        }
        return product;
    }

    private double quotient (double leftOperand, double rightOperand) {
        return leftOperand / rightOperand;
    }

    private double remainder (double leftOperand, double rightOperand) {
        return leftOperand % rightOperand;
    }

    private double minus (double leftOperand, double rightOperand) {
        return leftOperand - rightOperand;
    }

    private double minus (double operand) { return -operand; }

    private double random (double upperBound) {
        return upperBound * RANDOM_NUMBER_GENERATOR.nextDouble();
    }

    private double sine (double val) {
        return Math.sin(val);
    }

    private double cosine (double angle) {
        return Math.cos(angle);
    }

    private double tangent (double angle) {
        return Math.tan(angle);
    }

    private double arctangent (double angle) {
        return Math.atan(angle);
    }

    private double power (double base, double exponent) {
        return Math.pow(base, exponent);
    }

    private double pi () {
        return Math.PI;
    }
    
}
