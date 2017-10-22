package backend;

public class RootNode extends VarArgNode {

	@Override
	public double executeSelf(double... arguments) {
		// TODO Auto-generated method stub
		double finalResult = arguments.length == 0 ? 0 : arguments[arguments.length - 1];
		System.out.println("Final result: " + finalResult);
		return finalResult;
	}

}
