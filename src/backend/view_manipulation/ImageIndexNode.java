package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * Syntax node for returning the current turtle image index.
 *
 * @author Ben Schwennesen
 */
public class ImageIndexNode extends ViewNode {

    /**
     * Construct a node for returning the current turtle image index.
     *
     * @param viewController - the controller for objects in the view
     */
    public ImageIndexNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        return getViewController().getCurrentTurtleImageIndex();
    }

    @Override
    public int getDefaultNumberOfArguments() {
        return 0;
    }
}
