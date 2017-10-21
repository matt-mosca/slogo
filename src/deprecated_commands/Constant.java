package deprecated_commands;

import java.lang.reflect.Method;

public class Constant extends AbstractCommand {

	public static final String SET_VALUE = "setValue";
	public static final int ARGS_FOR_SET_VALUE = 1;
	private double value;
	
	public Constant(Method methodToInvoke, double value)  {
		//super(Constant.class, SET_VALUE, Constant.class.getMethod(SET_VALUE, double.class).getParameterCount());
		//super(setValue().);
		//setValue(value);
		//super(methodToInvoke);
		setValue(value);
	}

	public double execute() {return value;}

	private double getValue() { return value; }
	
	private void setValue(double value) {
		this.value = value;
	}
}
