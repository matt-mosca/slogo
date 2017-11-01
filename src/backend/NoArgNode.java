package backend;

/**
 * @author Ben Schwennesen
 */
public abstract class NoArgNode extends ValueNode {
	
	@Override
    public boolean canTakeVariableNumberOfArguments() {return false;}
    
    @Override
    public int getDefaultNumberOfArguments() {return 0;}
    
}
