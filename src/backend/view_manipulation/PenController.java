package backend.view_manipulation;

import backend.error_handling.SLogoException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;

/**
 * Controls the color and size of turtle pens.
 *
 * @author Ben Schwennesen
 */
public class PenController extends AbstractColorController implements SizeController {

    private int currentPenColorIndex;

    private DoubleProperty currentPenSize;

    private final double DEFAULT_PEN_SIZE = 1.0;

    /**
     * Construct the pen controller for a workspace.
     * @param paletteStorage - the color storage object
     */
    PenController(PaletteStorage paletteStorage) {
        super(paletteStorage);
        currentPenSize = new SimpleDoubleProperty(DEFAULT_PEN_SIZE);
    }

    @Override
    public double setSize(double size) {
        currentPenSize.setValue(size);
        return size;
    }

    @Override
    public double setColorIndex(int penColorIndex) throws SLogoException {
        currentPenColorIndex = penColorIndex;
        setColorProperty(currentPenColorIndex);
        return currentPenColorIndex;
    }

    @Override
    public double getColorIndex() { return currentPenColorIndex; }

    @Override
    public void addSizeListener(ChangeListener<Number> changeListener) {
        currentPenSize.addListener(changeListener);
    }
}
