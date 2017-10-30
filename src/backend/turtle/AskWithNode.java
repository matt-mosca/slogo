package backend.turtle;

import java.util.ArrayList;
import java.util.List;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

public class AskWithNode extends AskNode {

	SyntaxNode queryRoot;
	
	public AskWithNode(String commandString, TurtleFactory turtleFactory, SyntaxNode queryRoot, SyntaxNode commandsRoot) {
		super(commandString, turtleFactory, commandsRoot);
		this.queryRoot = queryRoot;
	}
	
	@Override
	public double executeSelf(double ... arguments) throws SLogoException {
		TurtleFactory turtleFactory = getTurtleFactory();
		int numTurtles = turtleFactory.getNumberTurtlesCreated();
		List<Integer> filteredIds = new ArrayList<>();
		for (int turtleNum = 1; turtleNum <= numTurtles; turtleNum ++) {
			turtleFactory.setQueryTurtleId(turtleNum);
			if (queryRoot.execute() != 0) {
				filteredIds.add(turtleNum);
			}
		}
		// Future queries will reference the active turtle
		turtleFactory.setQueryTurtleId(0);
		double[] idDoubleArgs = new double[filteredIds.size()];
		for (int index = 0; index < filteredIds.size(); index ++) {
			idDoubleArgs[index] = filteredIds.get(index);
		}
		return super.executeSelf(idDoubleArgs);
	}

}
