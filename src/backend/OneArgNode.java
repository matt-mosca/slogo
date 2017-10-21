package backend;

public abstract class OneArgNode extends ValueNode {

    @Override
    public boolean canTakeVariableNumberOfArguments() {return false;}
    
    @Override
    public int getDefaultNumberOfArguments() {return 1;}

}
