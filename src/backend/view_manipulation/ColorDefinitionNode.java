package backend.view_manipulation;

import backend.error_handling.SLogoException;

public class ColorDefinitionNode extends ViewNode {

    public ColorDefinitionNode(ViewController viewController) {
        super(viewController);
    }

    @Override
    public double executeSelf(double... arguments) throws SLogoException {
        double index = arguments[0];
        int red = (int) arguments[1], green = (int) arguments[2], blue = (int) arguments[3];
        return getViewController().setColorAtIndex(index, red, green, blue);
    }

    @Override
    public int getDefaultNumberOfArguments() {
        return 4;
    }
}
