package backend.view_manipulation;

import javafx.beans.value.ChangeListener;

/**
 * Controls the size of an object displayed in the frontend via commands interpreted by the backend.
 *
 * @author Ben Schwennesen
 */
public interface SizeController {

    /**
     * Add a listener for this controller.
     *
     * @param changeListener - listens for changes to the size
     */
    void addSizeListener(ChangeListener<Number> changeListener);

    /**
     * Set the current size of the object.
     *
     * @param size - the new size
     * @return the new size
     */
    double setSize(double size);

}
