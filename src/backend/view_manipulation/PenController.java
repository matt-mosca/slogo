package backend.view_manipulation;

import backend.error_handling.SLogoException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;

/**
 * @author Ben Schwennesen
 */
public class PenController extends AbstractColorController implements SizeController {

    private double currentPenColorIndex;

    private DoubleProperty currentPenSize;

    private final double DEFAULT_PEN_SIZE = 1.0;

    public PenController(PaletteStorage paletteStorage) {
        super(paletteStorage);
        currentPenSize = new SimpleDoubleProperty(DEFAULT_PEN_SIZE);
    }


    @Override
    public double setSize(double size) {
        currentPenSize.setValue(size);
        return size;
    }

    @Override
    public double setColorIndex(double penColorIndex) throws SLogoException {
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
