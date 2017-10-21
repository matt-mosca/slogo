package backend;

public abstract class VarArgNode extends ValueNode {

    @Override
    public boolean canTakeVariableNumberOfArguments() {return true;}
    
    @Override
    public int getDefaultNumberOfArguments() {return 2;}

}
