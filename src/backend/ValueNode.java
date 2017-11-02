package backend;

import backend.error_handling.SLogoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of a syntax tree that can be evaluated post-order. Provides an
 * interface for adding, saving and retrieving children, serializing sub-tree
 * and executing children / self separately (for debugging purposes)
 * 
 * 
 * @author Adithya Raghunathan
 */
public abstract class ValueNode implements SyntaxNode {

	private List<SyntaxNode> children;

	protected ValueNode() {
		children = new ArrayList<>();
	}

	/**
	 * Executes tree in post-order, recursively evaluating children and then
	 * executing root command based on resolved double values from children
	 */
	@Override
	public double execute() throws SLogoException {
		double[] arguments = executeChildren();
		return executeSelf(arguments);
	}

	/**
	 * Resolve child nodes
	 * 
	 * @return array of doubles corresponding to resolved children subtrees
	 * @throws SLogoException
	 */

	public double[] executeChildren() throws SLogoException {
		double[] arguments = new double[children.size()];
		for (int index = 0; index < arguments.length; index++) {
			arguments[index] = children.get(index).execute();
		}
		return arguments;
	}

	/**
	 * 
	 * @param arguments
	 * @return
	 * @throws SLogoException
	 */
	public abstract double executeSelf(double... arguments) throws SLogoException;

	/**
	 * 
	 * @return default number of arguments taken by this node if it is not taking
	 *         unlimited args
	 */
	public abstract int getDefaultNumberOfArguments();

	/**
	 * Add a SyntaxNode as a child of this ValueNode
	 * 
	 * @param child
	 *            the SyntaxNode to be added to this node's list of children,
	 *            addition order preserved
	 */
	public void addChild(SyntaxNode child) {
		children.add(child);
	}

	protected List<SyntaxNode> getChildren() {
		return children;
	}
}
