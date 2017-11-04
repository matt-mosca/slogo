package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * Syntax node for obtaining the color index of the current told turtles' pen color.
 *
 * @author Ben Schwennesen
 */
public class PenIndexNode extends ViewNode {

    /**
     * Construct a color index getting node.
     *
     * @param viewController - the controller for objects in the view
     */
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
