package backend;

import java.util.List;

/**
 * @author Adithya Raghunathan
 */
public class RootNode extends VarArgNode {
	
	@Override
	public double executeSelf(double... arguments) {
		// TODO Auto-generated method stub
		double finalResult = arguments.length == 0 ? 0 : arguments[arguments.length - 1];
		return finalResult;
	}
	
	public List<SyntaxNode> getChildren() {
		return super.getChildren();
	}

}
