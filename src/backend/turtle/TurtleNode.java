package backend.turtle;

import backend.ValueNode;

/**
 * @author Adithya Raghunathan
 */
public abstract class TurtleNode extends ValueNode {

	private TurtleController turtleController;

	public TurtleNode(String commandString, TurtleController turtleFactory) {
		super(commandString);
		this.turtleController = turtleFactory;
	}

	protected TurtleController getTurtleController() {
		return turtleController;
	}

	public boolean canTakeVariableNumberOfArguments() {
		return false;
	}

}
