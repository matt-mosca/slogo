package backend.logic_nodes;

import backend.VarArgNode;

/**
 * @author Ben Schwennesen
 */
public class EqualNode extends VarArgNode {

    @Override
    public double executeSelf(double... operands) {
        double firstOperand = operands[0];
        for (double operand : operands) {
            if (operand != firstOperand) {
                return 0;
            }
        }
        return 1;
    }

}