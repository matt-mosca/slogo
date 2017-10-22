package backend.logic_nodes;

import backend.VarArgNode;

public class OrNode extends VarArgNode {

    @Override
    public double executeSelf(double... operands) { return operands[0] != 0 || operands[1] != 0 ? 1 : 0; }
    
}
