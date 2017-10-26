package backend.view_manipulation;

import javafx.beans.value.ChangeListener;

/**
 * @author Ben Schwennesen
 */
public interface SizeController {

    void addSizeListener(ChangeListener<Number> changeListener);

    double setSize(double size);

}
