package backend.turtle;

import backend.ValueNode;

public abstract class TurtleNode extends ValueNode {

	private TurtleController turtleController;

	public TurtleNode(TurtleController turtleController) {
		this.turtleController = turtleController;
	}

	protected TurtleController getTurtleController() {
		return turtleController;
	}

	public boolean canTakeVariableNumberOfArguments() {
		return false;
	}

}
