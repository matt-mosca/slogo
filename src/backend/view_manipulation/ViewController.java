package backend.view_manipulation;

import backend.error_handling.SLogoException;
import backend.turtle.TurtleController;
import frontend.turtle_display.TurtleView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.Map;

/**
 * @author Ben Schwennesen
 */
public class ViewController {

    private PaletteStorage paletteStorage;
    private ImageStorage imageStorage;

    private ColorController backgroundController;
    // (below) same object referred to with different interfaces
    private SizeController penSizeController;
    private ColorController penColorController;

    private TurtleController turtleController;

    // default
    private int currentTurtleImageIndex = 1;

    public ViewController(PaletteStorage paletteStorage, TurtleView turtleView,
                          Rectangle turtleField, TurtleController turtleController) {
        this.paletteStorage = paletteStorage;
        PenController penController = new PenController(paletteStorage);
        this.penColorController = penController;
        this.penSizeController = penController;
        this.turtleController = turtleController;
        this.backgroundController = new BackgroundController(paletteStorage);
        addListeners(turtleView, turtleField, turtleController);
        this.imageStorage = new ImageStorage();
    }

    private void addListeners(TurtleView turtleView, Rectangle turtleField, TurtleController turtleController) {
        penSizeController.addSizeListener((fillProperty, oldSize, newSize) ->
                turtleController.getToldTurtleIds().forEach(toldTurtleId ->
                        turtleView.changeStrokeWidth(toldTurtleId, newSize.doubleValue())));

        penColorController.addColorListener((fillProperty, oldColor, newColor) ->
                turtleController.getToldTurtleIds().forEach(toldTurtleId ->
                        turtleView.changeDrawColor(toldTurtleId, newColor)));

        backgroundController.addColorListener((fillProperty, oldColor, newColor) ->
                turtleField.setFill(newColor));
    }

    double setColorAtIndex(int index, int red, int green, int blue) {
        return paletteStorage.setColorAtIndex(index, red, green, blue);
    }

    double setPenSize(double size) { return penSizeController.setSize(size); }

    double setPenColor(int index) throws SLogoException { return penColorController.setColorIndex(index); }

    double setBackgroundColor(int index) throws SLogoException {
        return backgroundController.setColorIndex(index);
    }

    double getPenColor() { return penColorController.getColorIndex(); }

    double setTurtleImage(int index) throws SLogoException {
        currentTurtleImageIndex = index;
        Image newImage = imageStorage.getImage(currentTurtleImageIndex);
        turtleController.getToldTurtleIds().forEach(turtleId -> turtleController.setTurtleImage(turtleId, newImage));
        return currentTurtleImageIndex;
    }

    int getCurrentTurtleImageIndex() { return currentTurtleImageIndex; }

    public Map<Integer, Image> getImageMap() { return imageStorage.getImageMap(); }
}
