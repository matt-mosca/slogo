package backend.view_manipulation;

import backend.error_handling.SLogoException;
import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;

/**
 * @author Ben Schwennesen
 */
public interface ColorController {

    void addColorListener(ChangeListener<Color> changeListener);

    double setColorIndex(int colorIndex) throws SLogoException;

    double getColorIndex();
}
