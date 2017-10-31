package backend;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import backend.control.ScopedStorage;
import backend.control.WorkspaceManager;
import backend.error_handling.ProjectBuildException;
import backend.error_handling.SLogoException;
import backend.turtle.TurtleController;
import backend.view_manipulation.PaletteStorage;
import backend.view_manipulation.ViewController;
import frontend.turtle_display.TurtleView;
import frontend.window_setup.IDEWindow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utilities.CommandGetter;

/**
 * @author Ben Schwennesen
 */
public class Controller {

	private Parser parser;
	private TurtleController turtleController;
	private ScopedStorage scopedStorage;
	private CommandGetter commandGetter;
	private PaletteStorage paletteStorage;
	private WorkspaceManager workspaceManager;
	private Debugger debugger;

	private final Map<Integer, Image> IMAGE_MAP;

	public Controller(ScopedStorage scopedStorage, TurtleView turtleView, Rectangle turtleField) throws SLogoException {
		this.scopedStorage = scopedStorage;
		this.paletteStorage = new PaletteStorage();
		turtleController = new TurtleController(turtleView, IDEWindow.TURTLEFIELD_WIDTH / 2,
				IDEWindow.TURTLEFIELD_HEIGHT / 2);
		this.commandGetter = new CommandGetter();
		ViewController viewController = new ViewController(paletteStorage, turtleView, turtleField, turtleController);
		IMAGE_MAP = viewController.getImageMap();
		this.parser = new Parser(turtleController, scopedStorage, viewController, commandGetter);
		workspaceManager = new WorkspaceManager();
		debugger = new Debugger();
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

	public Map<String, Double> retrieveAvailableVariables() {
		return scopedStorage.getAllAvailableVariables();
	}

	// already defined variable
	public void updateVariable(String name, double value) { scopedStorage.setVariable(name, value); }

	public Map<String, List<String>> retrieveDefinedFunctions() {
		return scopedStorage.getDefinedFunctions();
	}

	public Map<Integer, Color> retrieveAvailableColors() {
		return paletteStorage.getAvailableColors();
	}

	public Map<Integer, Image> retrieveAvailableImages() { return IMAGE_MAP; }

	// THE FOLLOWING 4 METHODS ARE TO SUPPORT BUTTONS
	public double moveTurtlesForward(double pixels) {
		return turtleController.moveCurrentTurtlesForward(pixels);
	}

	public double moveTurtlesBackward(double pixels) {
		return turtleController.moveCurrentTurtlesForward(-pixels);
	}

	public double turnTurtlesRight(double degrees) {
		return turtleController.rotateCurrentTurtles(true, degrees);
	}

	public double turnTurtlesLeft(double degrees) {
		return turtleController.rotateCurrentTurtles(false, degrees);
	}

	public double setPenDown(int index) {
		return turtleController.setPenDown(index + 1);
	}

	public double setPenUp(int index) {
		return turtleController.setPenUp(index + 1);
	}

	public double isPenDown(int index) {
		return turtleController.isPenDown(index + 1);
	}

	public void tellTurtle(int index) {
    	turtleController.setActiveTurtles(new Integer[] {index + 1});
	}

	public List<Integer> getToldTurtleIds() {
		return turtleController.getToldTurtleIds();
	}
	
	public void addOneTurtle() {
		int currentNumTurtles = turtleController.getNumberTurtlesCreated();
		turtleController.addTurtles(currentNumTurtles + 1);
	}

	public void saveWorkspaceToFile(String fileName) {
		workspaceManager.saveWorkspaceToFile(parser, scopedStorage, fileName);
	}

	public void loadWorkspaceFromFile(String fileName) throws SLogoException {
		workspaceManager.loadWorkspaceFromFile(parser, scopedStorage, fileName);
	}

	public void undo() throws SLogoException {
		if (parser.canUndo()) {
			resetView();
			parser.undo();
		}
	}

	public void redo() throws SLogoException {
		if (parser.canRedo()) {
			resetView();
			parser.redo();
		}
	}

	public void reset() {
		resetView();
	}

	private void resetView() {
		scopedStorage.clear();
		turtleController.clear();
	}
}
