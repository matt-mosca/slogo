package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * Syntax node used to set the background of the turtle area.
 *
 * @author Ben Schwennesen
 */
public class SetBackgroundNode extends ViewNode {

    /**
     * Construct a background setting node.
     *
     * @param viewController - the controller for objects in the view
     */
    public SetBackgroundNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().setBackgroundColor((int) arguments[0]);
    }

    @Override
    public int getDefaultNumberOfArguments() { return 1; }
}
