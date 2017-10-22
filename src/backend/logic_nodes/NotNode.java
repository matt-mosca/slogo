package backend.logic_nodes;

import backend.OneArgNode;

public class NotNode extends OneArgNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] == 0 ? 1 : 0; }
    
}
