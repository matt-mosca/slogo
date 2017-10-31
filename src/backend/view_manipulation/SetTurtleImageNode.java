package backend.view_manipulation;

import backend.error_handling.SLogoException;

public class SetTurtleImageNode extends ViewNode {

    public SetTurtleImageNode(String commandString, ViewController viewController) {
        super(commandString, viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().setTurtleImage((int) arguments[0]);
    }

    @Override
    public int getDefaultNumberOfArguments() { return 1; }
}
