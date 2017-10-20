package commands;

import backend.FunctionsStore;

import java.lang.reflect.Method;

public class StoreCommand extends AbstractCommand {

    public StoreCommand (Method methodToInvoke) {
        super(methodToInvoke);
    }

    private double storeVariables(FunctionsStore functions, String[] names, double[] values) {
        return functions.storeVariables(names, values);
    }
}
