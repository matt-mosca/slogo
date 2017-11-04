package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * Syntax node for defining a color at an index.
 *
 * @author Ben Schwennesen
 */
public class ColorDefinitionNode extends ViewNode {

    /**
     * Construct a node to define a color at an index.
     *
     * @param viewController - the controller for objects in the view
     */
    public ColorDefinitionNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        double index = arguments[0];
        int red = (int) arguments[1], green = (int) arguments[2], blue = (int) arguments[3];
        return getViewController().setColorAtIndex((int) index, red, green, blue);
    }

    @Override
    public int getDefaultNumberOfArguments() {
        return 4;
    }
}
