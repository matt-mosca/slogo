package backend.turtle;

import backend.error_handling.SLogoException;

public class TellNode extends TurtleNode {

	public TellNode(TurtleFactory turtleFactory) {
		super(turtleFactory);
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		return setIDs(arguments);
	}

	@Override
	public boolean canTakeVariableNumberOfArguments() {
		return true;
	}
	
	@Override
	public int getDefaultNumberOfArguments() {
		return 1;
	}
	
	protected double setIDs(double ... arguments) throws SLogoException {
		// Add every id to set of active turtles
		// Return last id
		Integer[] ids = new Integer[arguments.length];
		for (int index = 0; index < arguments.length; index ++) {
			ids[index] = (int) arguments[index];
		}
		return getTurtleFactory().setActiveTurtles(ids);
	}

}
