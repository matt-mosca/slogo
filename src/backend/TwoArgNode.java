package backend;

public abstract class TwoArgNode extends ValueNode {

    protected TwoArgNode(String commandString) {
		super(commandString);
	}

	@Override
    public boolean canTakeVariableNumberOfArguments() {return false;}
    
    @Override
    public int getDefaultNumberOfArguments() {return 2;}

}
