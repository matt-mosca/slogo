package backend.view_manipulation;

import backend.error_handling.SLogoException;

/**
 * Controls the color of the turtle area's background.
 *
 * @author Ben Schwennesen
 */
public class BackgroundController extends AbstractColorController {

    private int currentPenColorIndex;

    /**
     * Construct the background controller for a workspace.
     *
     * @param paletteStorage - the color storage object
     */
    BackgroundController(PaletteStorage paletteStorage) {
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

