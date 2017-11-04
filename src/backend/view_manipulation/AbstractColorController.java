package backend.view_manipulation;

import backend.error_handling.SLogoException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;

/**
 * Abstract representation of an object for controlling the color value of objects with colors set by commands.
 *
 * @author Ben Schwennesen
 */
public abstract class AbstractColorController implements ColorController {

    private PaletteStorage paletteStorage;

    private ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>();
    
    AbstractColorController(PaletteStorage paletteStorage) {
        this.paletteStorage = paletteStorage;
    }

    void setColorProperty(int index) throws SLogoException {
        colorProperty.setValue(paletteStorage.getColor(index));
    }

    @Override
    public void addColorListener(ChangeListener<Color> changeListener) {
        colorProperty.addListener(changeListener);
    }
}
