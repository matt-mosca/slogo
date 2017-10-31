package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class SetBackgroundNode extends ViewNode
{
    public SetBackgroundNode(String commandString, ViewController viewController) {
        super(commandString, viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().setBackgroundColor((int) arguments[0]);
    }

    @Override
    public int getDefaultNumberOfArguments() { return 1; }
}
