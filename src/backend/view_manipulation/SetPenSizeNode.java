package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class SetPenSizeNode extends ViewNode {

    public SetPenSizeNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().setPenSize(arguments[0]);
    }

    @Override
    public int getDefaultNumberOfArguments() { return 1; }
}
