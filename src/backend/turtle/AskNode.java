package backend.turtle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import backend.SyntaxNode;
import backend.error_handling.SLogoException;

public class AskNode extends TellNode {

	SyntaxNode commandsRoot;
	
	public AskNode(TurtleFactory turtleFactory, SyntaxNode commandsRoot) {
		super(turtleFactory);
		this.commandsRoot = commandsRoot;
	}

	@Override
	public double executeSelf(double... arguments) throws SLogoException {
		TurtleFactory turtleFactory = getTurtleFactory();
		// Need to save original list of ids
		Set<Integer> currentlyToldTurtleIds = turtleFactory.getToldTurtles();
		setIDs(arguments);
		// Execute root of commands list for every id in argument
		double result = 0.0;
		for (int index = 0; index < arguments.length; index ++) {
			result = commandsRoot.execute();			
		}
		// Restore original lsit of ids
		// Prefer casting of underlying collection to creating new list?
		turtleFactory.setActiveTurtles((Integer[]) currentlyToldTurtleIds.toArray());
		return result;
	}

}
