package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class BackgroundController extends AbstractColorController {

    private double currentPenColorIndex;

    public BackgroundController(PaletteStorage paletteStorage) {
        super(paletteStorage);
    }

    @Override
    public double setColorIndex(double backgroundColorIndex) throws SLogoException {
        currentPenColorIndex = backgroundColorIndex;
        setColorProperty(currentPenColorIndex);
        return currentPenColorIndex;
    }

    // public good tradeoff in order to define interface contract
    @Override
    public double getColorIndex() { return currentPenColorIndex; }
}

