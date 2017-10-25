package backend;

import backend.control.ScopedStorage;
import backend.error_handling.SLogoException;
import backend.turtle.TurtleFactory;
import frontend.turtle_display.TurtleView;

import javafx.scene.shape.Rectangle;
import utilities.PaletteStorage;

import java.util.List;
import java.util.Map;

public class Controller {

    private Parser parser;
    private TurtleFactory turtleFactory;
    private ScopedStorage scopedStorage;
    private PaletteStorage paletteStorage;

    public Controller(ScopedStorage scopedStorage, TurtleView turtleView, Rectangle turtleField) {
        this.turtleFactory = new TurtleFactory(turtleView);
        this.scopedStorage = scopedStorage;
        this.parser = new Parser(turtleFactory, scopedStorage);
        this.paletteStorage = new PaletteStorage();
    }

    public void setLanguage(String language) throws SLogoException {
        parser.setLanguage(language);
    }

    public boolean validateCommand(String commandString) throws SLogoException {
        return parser.validateCommand(commandString);
    }

    public void executeCommand(String commandString) throws SLogoException {
         parser.executeCommand(commandString);
    }

    public Map<String, Double> retrieveAvailableVariables () {
        return scopedStorage.getAllAvailableVariables();
    }

    public Map<String, List<String>> retrieveDefinedFunctions () {
        return scopedStorage.getDefinedFunctions();
    }
}
