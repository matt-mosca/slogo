package commands;

import java.util.Random;

/**
 * @author Ben Schwennesen
 */
public class MathCommand extends AbstractCommand {

    private final Random RANDOM_NUMBER_GENERATOR;

    public MathCommand(Class thisClass, String methodToInvoke, Class[] parameters) throws NoSuchMethodException {
        super(thisClass, methodToInvoke, parameters);
        RANDOM_NUMBER_GENERATOR = new Random();
    }

    private double sum (double leftValue, double rightValue) {
        return leftValue + rightValue;
    }

    private double difference (double leftValue, double rightValue) {
        return leftValue - rightValue;
    }

    private double product (double leftValue, double rightValue) {
        return leftValue * rightValue;
    }

    private double quotient (double leftValue, double rightValue) {
        return leftValue / rightValue;
    }

    private double remainder (double leftValue, double rightValue) {
        return leftValue % rightValue;
    }

    private double minus (double leftValue, double rightValue) {
        return leftValue - rightValue;
    }

    private double minus (double value) {
        return -value;
    }

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
