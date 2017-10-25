package backend.view_manipulation;

import backend.error_handling.SLogoException;
import frontend.turtle_display.TurtleView;
import javafx.scene.shape.Rectangle;

/**
 * @author Ben Schwennesen
 */
public class ViewController {

    private PaletteStorage paletteStorage;
    private ColorController backgroundController;
    // (below) same object referred to with different interfaces
    private SizeController penSizeController;
    private ColorController penColorController;

    public ViewController(PaletteStorage paletteStorage, TurtleView turtleView, Rectangle turtleField) {
        this.paletteStorage = paletteStorage;
        PenController penController = new PenController(paletteStorage);
        this.penColorController = penController;
        this.penSizeController = penController;
        this.backgroundController = new BackgroundController(paletteStorage);
        penSizeController.addSizeListener((fillProperty, oldSize, newSize) ->
                turtleView.changeStrokeWidth(newSize.doubleValue()));
        penController.addColorListener((fillProperty, oldColor, newColor) ->
                turtleView.changeDrawColor(newColor));
        backgroundController.addColorListener((fillProperty, oldColor, newColor) ->
                turtleField.setFill(newColor));
    }

    double setColorAtIndex(double index, int red, int green, int blue) {
        return paletteStorage.setColorAtIndex(index, red, green, blue);
    }

    double setPenSize(double size) { return penSizeController.setSize(size); }

    double setPenColor(double index) throws SLogoException { return penColorController.setColorIndex(index); }

    double setBackgroundColor(double index) throws SLogoException {
        return backgroundController.setColorIndex(index);
    }

    double getPenColor() { return penColorController.getColorIndex(); }
}
