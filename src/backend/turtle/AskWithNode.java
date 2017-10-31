package backend.turtle;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adithya Raghunathan
 */
public class AskWithNode extends AskNode {

	SyntaxNode queryRoot;
	
	public AskWithNode(String commandString, TurtleController turtleController, SyntaxNode queryRoot, SyntaxNode commandsRoot) {
		super(commandString, turtleController, commandsRoot);
		this.queryRoot = queryRoot;
	}
	
	@Override
	public double executeSelf(double ... arguments) throws SLogoException {
		TurtleController turtleController = getTurtleController();
		int numTurtles = turtleController.getNumberTurtlesCreated();
		List<Integer> filteredIds = new ArrayList<>();
		for (int turtleNum = 1; turtleNum <= numTurtles; turtleNum ++) {
			turtleController.setQueryTurtleId(turtleNum);
			if (queryRoot.execute() != 0) {
				filteredIds.add(turtleNum);
			}
		}
		// Future queries will reference the active turtle
		turtleController.setQueryTurtleId(0);
		double[] idDoubleArgs = new double[filteredIds.size()];
		for (int index = 0; index < filteredIds.size(); index ++) {
			idDoubleArgs[index] = filteredIds.get(index);
		}
		return super.executeSelf(idDoubleArgs);
	}

}
