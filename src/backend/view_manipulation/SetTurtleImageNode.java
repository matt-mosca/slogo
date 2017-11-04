package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * Syntax node for setting the currently told turtles' images.
 *
 * @author Ben Schwennesen
 */
public class SetTurtleImageNode extends ViewNode {

    /**
     * Construct a turtle image setting node.
     *
     * @param viewController - the controller for objects in the view
     */
    public SetTurtleImageNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().setTurtleImage((int) arguments[0]);
    }

    @Override
    public int getDefaultNumberOfArguments() { return 1; }
}
