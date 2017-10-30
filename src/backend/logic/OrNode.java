package backend.logic;

import backend.VarArgNode;

import java.util.Arrays;

/**
 * @author Ben Schwennesen
 */
public class OrNode extends VarArgNode {

    @Override
    public double executeSelf(double... operands) {
        for (double operand : operands) {
            if (operand != 0) {
                return 1;
            }
        }
        return 0;
    }
    
}
