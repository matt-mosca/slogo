package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * Syntax node for setting the size of the currently told turtles' pens.
 *
 * @author Ben Schwennesen
 */
public class SetPenSizeNode extends ViewNode {

    /**
     * Construct a pen size setting node.
     *
     * @param viewController - the controller for objects in the view
     */
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
