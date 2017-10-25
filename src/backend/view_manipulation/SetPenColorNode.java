package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class SetPenColorNode extends ViewNode {

    public SetPenColorNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().setPenColor(arguments[0]);
    }

    @Override
    public int getDefaultNumberOfArguments() { return 1; }
}
