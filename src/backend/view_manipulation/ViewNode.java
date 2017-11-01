package backend.view_manipulation;

import backend.ValueNode;

/**
 * @author Ben Schwennesen
 */
public abstract class ViewNode extends ValueNode {

	private ViewController viewController;

	public ViewNode(ViewController viewController) {
		this.viewController = viewController;
	}

	@Override
	public boolean canTakeVariableNumberOfArguments() {
		return false;
	}

	protected ViewController getViewController() {
		return viewController;
	}
}
