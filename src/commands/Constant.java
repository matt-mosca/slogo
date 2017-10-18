package commands;

public class Constant extends AbstractCommand {

	public static final String SET_VALUE = "setValue";
	public static final int ARGS_FOR_SET_VALUE = 1;
	private double value;
	
	public Constant(double value) throws NoSuchMethodException {
		//super(Constant.class, SET_VALUE, Constant.class.getMethod(SET_VALUE, double.class).getParameterCount());
		super(Constant.class, SET_VALUE, ARGS_FOR_SET_VALUE);
		setValue(value);
	}
	
	@Override
	public double execute(Double... arguments) {
		return value;
	}
	
	private void setValue(double value) {
		this.value = value;
	}
}
