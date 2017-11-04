package backend.view_manipulation;

import backend.ValueNode;

/**
 * Abstract syntax node for manipulating view objects.
 *
 * @author Ben Schwennesen
 */
public abstract class ViewNode extends ValueNode {

	private ViewController viewController;

	/**
	 * Construct an abstract view node.
	 * @param viewController - the controller for objects in the view
	 */
	ViewNode(ViewController viewController) {
		this.viewController = viewController;
	}

	@Override
	public boolean canTakeVariableNumberOfArguments() {
		return false;
	}

	/**
	 * @return the view controller for the current workspace
	 */
	protected ViewController getViewController() {
		return viewController;
	}
}
