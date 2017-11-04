package backend.view_manipulation;

import backend.error_handling.SLogoException;
import backend.turtle.TurtleController;
import frontend.turtle_display.TurtleView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.Map;

/**
 * Controller for objects in the view, which must be configurable by commands parsed in the backend.
 *
 * @author Ben Schwennesen
 */
public class ViewController {

    private ImageStorage imageStorage;
    private PaletteStorage paletteStorage;

    private TurtleController turtleController;
    private ColorController backgroundController;

    // two below are the same object referred to with different interfaces
    private SizeController penSizeController;
    private ColorController penColorController;

    // default
    private int currentTurtleImageIndex = 1;

    /**
     * Construct the view controller for executing commands affecting the frontend, as parsed by the backend.
     *
     * @param paletteStorage - color storage object
     * @param turtleView - the turtle view object
     * @param turtleField - the turtle display area
     * @param turtleController - the controller/communicator of turtle's backend representations
     */
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

    /**
     * Pass a color definition command to be executed to the storage class.
     *
     * @see PaletteStorage
     */
    double setColorAtIndex(int index, int red, int green, int blue) {
        return paletteStorage.setColorAtIndex(index, red, green, blue);
    }

    /**
     * Pass a pen size setting command to be execute to the pen size controller.
     *
     * @see PenController
     * @see SizeController
     */
    double setPenSize(double size) { return penSizeController.setSize(size); }

    /**
     * Pass a pen color setting command to be executed to the pen color controller.
     *
     * @see PenController
     * @see ColorController
     */
    double setPenColor(int index) throws SLogoException { return penColorController.setColorIndex(index); }

    /**
     * Pass a background color setting command to the background color controller.
     *
     * @see BackgroundController
     * @see ColorController
     */
    double setBackgroundColor(int index) throws SLogoException {
        return backgroundController.setColorIndex(index);
    }

    /**
     * Pass along a turtle image setting command to the turtle controller.
     *
     * @see TurtleController
     */
    double setTurtleImage(int index) throws SLogoException {
        currentTurtleImageIndex = index;
        Image newImage = imageStorage.getImage(currentTurtleImageIndex);
        turtleController.getToldTurtleIds().forEach(turtleId -> turtleController.setTurtleImage(turtleId, newImage));
        return currentTurtleImageIndex;
    }

    /**
     * Pass along the current told turtles' pen color.
     *
     * @see PenController
     * @see ColorController
     */
    double getPenColor() { return penColorController.getColorIndex(); }

    /**
     * @return the index of the currently told turtle(s)' image
     */
    int getCurrentTurtleImageIndex() { return currentTurtleImageIndex; }

    /**
     * Pass along the defined colors map.
     *
     * @see PaletteStorage
     */
    public Map<Integer, Image> getImageMap() { return imageStorage.getImageMap(); }
}
