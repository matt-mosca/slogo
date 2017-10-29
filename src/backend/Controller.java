package backend;

import backend.control.ScopedStorage;
import backend.control.WorkspaceManager;
import backend.error_handling.SLogoException;
import backend.error_handling.TurtleOutOfScreenException;
import backend.error_handling.WorkspaceFileNotFoundException;
import backend.turtle.TurtleFactory;
import backend.view_manipulation.PaletteStorage;
import backend.view_manipulation.ViewController;
import frontend.turtle_display.TurtleView;
import frontend.window_setup.IDEWindow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Ben Schwennesen
 */
public class Controller {

    private Parser parser;
    private ScopedStorage scopedStorage;
    private PaletteStorage paletteStorage;
    private TurtleFactory turtleFactory;
    private WorkspaceManager workspaceManager;

    public Controller(ScopedStorage scopedStorage, TurtleView turtleView, Rectangle turtleField) {
        this.scopedStorage = scopedStorage;
        this.paletteStorage = new PaletteStorage();
        turtleFactory = new TurtleFactory(turtleView, IDEWindow.TURTLEFIELD_WIDTH / 2, IDEWindow.TURTLEFIELD_HEIGHT / 2);
        ViewController viewController = new ViewController(paletteStorage, turtleView, turtleField);
        this.parser = new Parser(turtleFactory, scopedStorage, viewController);
        workspaceManager = new WorkspaceManager();
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

    public Map<Double, Color> retrieveAvailableColors() {
        return paletteStorage.getAvailableColors();
    }
    
    // THE FOLLOWING 4 METHODS ARE TO SUPPORT BUTTONS
    public double moveTurtlesForward(double pixels) throws TurtleOutOfScreenException {
    		return turtleFactory.moveCurrentTurtlesForward(pixels);
    }
    
    public double moveTurtlesBackward(double pixels) throws TurtleOutOfScreenException {
    		return turtleFactory.moveCurrentTurtlesForward(-pixels);
    }
    
    public double turnTurtlesRight(double degrees) {
    		return turtleFactory.rotateCurrentTurtles(true, degrees);
    }
    
    public double turnTurtlesLeft(double degrees) {
    		return turtleFactory.rotateCurrentTurtles(false, degrees);
    }
    
    public double setPenDown(int index) {
    	return turtleFactory.setPenDown(index);
    }
    
    public double setPenUp(int index) {
    	return turtleFactory.setPenUp(index);
    }
    
    public void saveWorkspaceToFile(String fileName) {
    	
    		workspaceManager.saveWorkspaceToFile(scopedStorage, fileName);
    }
    
    public void loadWorkspaceFromFile(String fileName) throws SLogoException {
    		workspaceManager.loadWorkspaceFromFile(scopedStorage, fileName);
    }
}
