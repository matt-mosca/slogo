package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * Syntax node for setting the currently told turtles' pen colors.
 *
 * @author Ben Schwennesen
 */
public class SetPenColorNode extends ViewNode {

    /**
     * Construct a pen color setting node.
     *
     * @param viewController - the controller for objects in the view
     */
    public SetPenColorNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().setPenColor((int) arguments[0]);
    }

    @Override
    public int getDefaultNumberOfArguments() { return 1; }
}
