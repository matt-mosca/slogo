package backend;

import java.util.List;

public class Constant extends Command {

	// TODO - Try to eliminate need for this if possible
	public static final String CONSTANT = "CONSTANT";

	private double value;

	Constant(double value) {
		// ugly ... avoid by not sub-classing Command?
		// but want benefit of inheriting execute() and getSyntaxNodeType() methods
		// for uniform handling by parser
		super(CommandType.CONSTANT, CONSTANT);
		this.value = value;
	}

	@Override
	double evaluate(List<Double> operands) {
		return value;
	}
}
