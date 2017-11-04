package backend.view_manipulation;

import backend.error_handling.SLogoException;
import javafx.beans.value.ChangeListener;
import javafx.scene.paint.Color;

/**
 * Interface for controlling the color of an object whose color can be set by a command.
 *
 * @author Ben Schwennesen
 */
public interface ColorController {

    /**
     * Add a listener for this controller.
     *
     * @param changeListener - listens for changes to the color
     */
    void addColorListener(ChangeListener<Color> changeListener);

    /**
     * Set the color to the color represented by the passed index.
     *
     * @param colorIndex - new color index
     * @return the new color index
     * @throws SLogoException - in the case that the color is undefined
     */
    double setColorIndex(int colorIndex) throws SLogoException;

    /**
     * @return the index of the current color
     */
    double getColorIndex();
}
