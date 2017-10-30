package backend;

public abstract class NoArgNode extends ValueNode {
	
    protected NoArgNode(String commandString) {
		super(commandString);
	}

	@Override
    public boolean canTakeVariableNumberOfArguments() {return false;}
    
    @Override
    public int getDefaultNumberOfArguments() {return 0;}
    
}
