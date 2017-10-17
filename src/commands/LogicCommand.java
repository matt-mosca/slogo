package commands;

public class LogicCommand extends AbstractCommand {

    public LogicCommand (Class thisClass, String methodToInvoke, int numberOfDoubleParameters) throws NoSuchMethodException {
        super(thisClass, methodToInvoke, numberOfDoubleParameters);
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
