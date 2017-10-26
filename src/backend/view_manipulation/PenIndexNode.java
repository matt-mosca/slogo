package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class PenIndexNode extends ViewNode {

    public PenIndexNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().getPenColor();
    }

    @Override
    public int getDefaultNumberOfArguments() {
        return 0;
    }
}
