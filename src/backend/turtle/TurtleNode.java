package backend.turtle;

import backend.ValueNode;

public abstract class TurtleNode extends ValueNode {

	private TurtleFactory turtleFactory;

	public TurtleNode(String commandString, TurtleFactory turtleFactory) {
		super(commandString);
		this.turtleFactory = turtleFactory;
	}

	protected TurtleFactory getTurtleFactory() {
		return turtleFactory;
	}

	public boolean canTakeVariableNumberOfArguments() {
		return false;
	}

}
