package backend;

public abstract class TwoArgNode extends ValueNode {

    @Override
    public boolean canTakeVariableNumberOfArguments() {return false;}
    
    @Override
    public int getDefaultNumberOfArguments() {return 2;}

}
