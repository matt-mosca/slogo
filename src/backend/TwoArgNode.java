package backend;

/**
 * @author Adithya Raghunathan
 */
public abstract class TwoArgNode extends ValueNode {

	@Override
    public boolean canTakeVariableNumberOfArguments() {return false;}
    
    @Override
    public int getDefaultNumberOfArguments() {return 2;}

}
