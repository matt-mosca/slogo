package backend.control;

import backend.SyntaxNode;
import backend.math.ConstantNode;

/**
 * Syntax node for executing do-times command.
 *
 * @author Ben Schwennesen
 */
public class DoTimesNode extends LoopNode {

	private static final SyntaxNode START_EXPRESSION = new ConstantNode(1);
	private static final SyntaxNode INCREMENT_EXPRESSION = new ConstantNode(1);

	/**
	 * Construct a do-times command node.
	 *
	 * @param store - the object used to store the current workspace's functions and variables
	 * @param iterationVariable - the variable used to access the current iteration number
	 * @param limitExpression - the syntax node representing the iteration limit
	 * @param subtree - the syntax node to execute at each iteration
	 */
	public DoTimesNode(ScopedStorage store, String iterationVariable, SyntaxNode limitExpression,
			SyntaxNode subtree) {
		super(store, iterationVariable, START_EXPRESSION, limitExpression, INCREMENT_EXPRESSION, subtree);
	}


}
