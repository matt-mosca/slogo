package backend;

import backend.error_handling.SLogoException;

import java.util.ArrayList;
import java.util.List;

public abstract class ValueNode implements SyntaxNode {

	List<SyntaxNode> children;
	private String serializedString;
	
	protected ValueNode(String commandString) {
		children = new ArrayList<>();
		this.serializedString = commandString;
	}
	
	// Execute in post-order
	@Override
	public double execute() throws SLogoException {
		double[] arguments = executeChildren();
		return executeSelf(arguments);
	}
	
	public double[] executeChildren() throws SLogoException {
		double[] arguments = new double[children.size()];
		for (int index = 0; index < arguments.length; index ++) {
			arguments[index] = children.get(index).execute();
			serializedString += Double.toString(arguments[index]);
		}
		return arguments;
	}
		
	@Override
	public String serialize() {
		return serializedString;
	}	
	
	public abstract double executeSelf(double ... arguments) throws SLogoException;
	
	public abstract int getDefaultNumberOfArguments();
	
	public void addChild(SyntaxNode child) {
		children.add(child);
	}
	
	protected List<SyntaxNode> getChildren() {
		return children;
	}
}
