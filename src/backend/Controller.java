package backend;

import backend.control.ScopedStorage;
import backend.control.WorkspaceManager;
import backend.error_handling.ProjectBuildException;
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
import utilities.CommandGetter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Ben Schwennesen
 */
public class Controller {

    private Parser parser;
    private TurtleFactory turtleFactory;
    private ScopedStorage scopedStorage;
    private CommandGetter commandGetter;
    private PaletteStorage paletteStorage;
    private WorkspaceManager workspaceManager;

    public Controller(ScopedStorage scopedStorage, TurtleView turtleView, Rectangle turtleField) {
        this.scopedStorage = scopedStorage;
        this.paletteStorage = new PaletteStorage();
        turtleFactory = new TurtleFactory(turtleView, IDEWindow.TURTLEFIELD_WIDTH / 2, IDEWindow.TURTLEFIELD_HEIGHT / 2);
        this.commandGetter = new CommandGetter();
        ViewController viewController = new ViewController(paletteStorage, turtleView, turtleField);
        this.parser = new Parser(turtleFactory, scopedStorage, viewController, commandGetter);
        workspaceManager = new WorkspaceManager();
    }

    // To support switching of language through front end
    public void setLanguage(String language) throws SLogoException {
        try {
            commandGetter.setLanguage(language);
        } catch (IOException e) {
            // Can only be because language passed in is not supported
            throw new ProjectBuildException();
        }
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
    public double moveTurtlesForward(double pixels) {
    		return turtleFactory.moveCurrentTurtlesForward(pixels);
    }
    
    public double moveTurtlesBackward(double pixels){
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
    
    public double isPenDown(int index) {
    	return turtleFactory.isPenDown(index);
    }
    
    public void saveWorkspaceToFile(String fileName) {
    		workspaceManager.saveWorkspaceToFile(scopedStorage, fileName);
    }
    
    public void loadWorkspaceFromFile(String fileName) throws SLogoException {
    		workspaceManager.loadWorkspaceFromFile(scopedStorage, fileName);
    }
}
