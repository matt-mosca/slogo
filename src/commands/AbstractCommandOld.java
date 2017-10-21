package commands;

import apis.Command;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Ben Schwennesen
 */
public abstract class AbstractCommandOld implements Command {

	private final Method METHOD_TO_INVOKE;

	public AbstractCommandOld(Method methodToInvoke) {
		METHOD_TO_INVOKE = methodToInvoke;
	}

	public boolean takesVariableArguments() {
		Class[] parameterTypes = METHOD_TO_INVOKE.getParameterTypes();
		for (Class parameterType : parameterTypes) {
			if (parameterType.isArray()) {
				return true;
			}
		}
		return false;
	}

	public int getNumberOfArguments() {
		return METHOD_TO_INVOKE.getParameterTypes().length;
	}

	@Override
	public double execute(double ... arguments) throws IllegalAccessException, InvocationTargetException {
		METHOD_TO_INVOKE.setAccessible(true);
		return 0.0;
	}
}
