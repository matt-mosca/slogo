package backend.turtle;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

import java.util.Set;

public class AskNode extends TellNode {

	SyntaxNode commandsRoot;
	
	public AskNode(String commandString, TurtleController turtleController, SyntaxNode commandsRoot) {
		super(commandString, turtleController);
		this.commandsRoot = commandsRoot;
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		TurtleController turtleController = getTurtleController();
		// Need to save snapshot of original list of ids
		Set<Integer> currentlyToldTurtleIds = turtleController.getToldTurtles();
		setIDs(arguments);
		// Execute root of commands list 
		double result = 0.0;
		result = commandsRoot.execute();
		// Restore original list of ids
		// Prefer casting of underlying collection to creating new list?
		turtleController.setActiveTurtles(currentlyToldTurtleIds.toArray(new Integer[0]));
		return result;
	}

}
