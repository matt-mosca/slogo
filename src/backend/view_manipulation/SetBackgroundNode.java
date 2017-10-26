package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class SetBackgroundNode extends ViewNode
{
    public SetBackgroundNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().setBackgroundColor(arguments[0]);
    }

    @Override
    public int getDefaultNumberOfArguments() { return 1; }
}
