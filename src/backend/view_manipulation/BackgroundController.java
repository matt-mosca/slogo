package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * @author Ben Schwennesen
 */
public class BackgroundController extends AbstractColorController {

    private int currentPenColorIndex;

    public BackgroundController(PaletteStorage paletteStorage) {
        super(paletteStorage);
    }

    @Override
    public double setColorIndex(int backgroundColorIndex) throws SLogoException {
        currentPenColorIndex = backgroundColorIndex;
        setColorProperty(currentPenColorIndex);
        return currentPenColorIndex;
    }

    // public good trade-off in order to define interface contract
    @Override
    public double getColorIndex() { return currentPenColorIndex; }
}

