package backend.view_manipulation;

import backend.error_handling.SLogoException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;

/**
 *
 *
 * @author Ben Schwennesen
 */
public abstract class AbstractColorController implements ColorController {

    private PaletteStorage paletteStorage;

    private ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>();

    public AbstractColorController(PaletteStorage paletteStorage) {
        this.paletteStorage = paletteStorage;
    }

    protected void setColorProperty(int index) throws SLogoException {
        colorProperty.setValue(paletteStorage.getColor(index));
    }

    @Override
    public void addColorListener(ChangeListener<Color> changeListener) {
        colorProperty.addListener(changeListener);
    }
}
