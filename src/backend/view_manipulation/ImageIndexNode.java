package backend.view_manipulation;

import backend.error_handling.SLogoException;

public class ImageIndexNode extends ViewNode {

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
