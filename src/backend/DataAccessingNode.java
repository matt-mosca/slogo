package backend;

public abstract class DataAccessingNode implements SyntaxNode {

	private FunctionsStore store;
	
	public DataAccessingNode(FunctionsStore store) {
		this.store = store;
	}
	
}
