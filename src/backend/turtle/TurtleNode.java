package backend.turtle;

import backend.ValueNode;

/**
 * @author Adithya Raghunathan
 */
public abstract class TurtleNode extends ValueNode {

	private TurtleController turtleController;

	public TurtleNode(TurtleController turtleFactory) {
		this.turtleController = turtleFactory;
	}

	protected TurtleController getTurtleController() {
		return turtleController;
	}

	public boolean canTakeVariableNumberOfArguments() {
		return false;
	}

}
