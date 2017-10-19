package commands;

import java.lang.reflect.Method;

/**
 * @author Ben Schwennesen
 */
public class LogicCommand extends AbstractCommand {

    public LogicCommand (Method methodToInvoke) {
        super(methodToInvoke);
    }

    private double less (double leftValue, double rightValue) {
        return leftValue < rightValue ? 1 : 0;
    }

    private double greater (double leftValue, double rightValue) {
        return leftValue > rightValue ? 1 : 0;
    }

    private double equal (double leftValue, double rightValue) {
        return leftValue == rightValue ? 1 : 0;
    }

    private double notEqual (double leftValue, double rightValue) {
        return leftValue != rightValue ? 1 : 0;
    }

    private double and (double leftValue, double rightValue) {
        return leftValue != 0 &&  rightValue != 0 ? 1 : 0;
    }

    private double or (double leftValue, double rightValue) {
        return leftValue != 0 ||  rightValue != 0 ? 1 : 0;
    }

    private double not (double value) {
        return value == 0 ? 1 : 0;
    }
}
