package deprecated_commands;

import backend.control.ScopedStorage;

import java.lang.reflect.Method;

/**
 * @deprecated
 */
public class StoreCommand extends AbstractCommandOld {

    public StoreCommand (Method methodToInvoke) {
        super(methodToInvoke);
    }

    private double storeVariables(ScopedStorage functions, String[] names, double[] values) {
        //return functions.storeVariables(names, values);
        return 0;
    }
}
