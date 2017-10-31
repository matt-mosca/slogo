package backend;

/**
 * @author Ben Schwennesen
 */
public abstract class OneArgNode extends ValueNode {

    protected OneArgNode(String commandString) {
		super(commandString);
	}

	@Override
    public boolean canTakeVariableNumberOfArguments() {return false;}
    
    @Override
    public int getDefaultNumberOfArguments() {return 1;}

}
