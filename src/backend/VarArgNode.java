package backend;

public abstract class VarArgNode extends ValueNode {

    protected VarArgNode(String commandString) {
		super(commandString);
	}

	@Override
    public boolean canTakeVariableNumberOfArguments() {return true;}
    
    @Override
    public int getDefaultNumberOfArguments() {return 2;}

}
