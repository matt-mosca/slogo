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
 * Central controller for communication between view and model.
 *
 * @author Ben Schwennesen
 */
public class Controller {

	private Parser parser;
	private TurtleController turtleController;
	private ScopedStorage scopedStorage;
	private CommandGetter commandGetter;
	private PaletteStorage paletteStorage;
	private WorkspaceManager workspaceManager;

	private final Map<Integer, Image> IMAGE_MAP;

	/**
	 * Construct the controller for model-view communication.
	 *
	 * @param scopedStorage - the object for storing functions and variables (passed since the view listens to it)
	 * @param turtleView - the object for manipulating turtle's frontend representations
	 * @param turtleField - the area where turtles are displayed
	 * @throws SLogoException - in the case that the project fails to build
	 */
	public Controller(ScopedStorage scopedStorage, TurtleView turtleView, Rectangle turtleField) throws SLogoException {
		this.scopedStorage = scopedStorage;
		this.paletteStorage = new PaletteStorage();
		turtleController = new TurtleController(turtleView, IDEWindow.TURTLEFIELD_WIDTH / 2,
				IDEWindow.TURTLEFIELD_HEIGHT / 2);
		this.commandGetter = new CommandGetter();
		ViewController viewController = new ViewController(paletteStorage, turtleView, turtleField, turtleController);
		IMAGE_MAP = viewController.getImageMap();
		ParserUtils parserUtils = new ParserUtils();
		this.parser = new Parser(turtleController, scopedStorage, viewController, commandGetter, parserUtils);
		workspaceManager = new WorkspaceManager();
	}

	/**
	 * Change the language used to parse commands.
	 *
	 * @param language - the desired language as a string
	 * @throws SLogoException - in the case that the language properties file is not found
	 */
	public void setLanguage(String language) throws SLogoException {
		try {
			commandGetter.setLanguage(language);
		} catch (IOException e) {
			// can only be because language passed in is not supported
			throw new ProjectBuildException();
		}
	}

	/**
	 * Validate that an entered command is executable (that is, free of syntax and other errors).
	 *
	 * @param commandString - a string of an arbitrary number of commands entered by the user
	 * @return true if the command is valid, false (or exception thrown) otherwise
	 * @throws SLogoException - in the case that some commands contain errors (fail fast)
	 */
	public boolean validateCommand(String commandString) throws SLogoException {
		return parser.validateCommand(commandString);
	}

	/**
	 * Execute a validated command.
	 *
	 * @param commandString - a string of an arbitrary number of commands entered by the user, which should be
	 *                         validated in advance (but will work regardless)
	 * @throws SLogoException - in the case that some commands contain errors (fail fast)
	 */
	public void executeCommand(String commandString) throws SLogoException {
		parser.executeCommand(commandString);
	}

	/**
	 * Retrieve currently defined variables for displaying in the frontend.
	 *
	 * @return a map from variable names to their values
	 */
	public Map<String, Double> retrieveAvailableVariables() {
		return scopedStorage.getAllAvailableVariables();
	}

	/**
	 * Update the value of an already defined variable when the user sets it via the frontend.
	 *
	 * @param name - the variable's name
	 * @param value - the variable's new value
	 */
	public void updateVariable(String name, double value) { scopedStorage.setVariable(name, value); }

	/**
	 * Retrieve currently defined functions for displaying in the frontend.
	 *
	 * @return a map from function names to a list of their parameter names
	 */
	public Map<String, List<String>> retrieveDefinedFunctions() {
		return scopedStorage.getDefinedFunctions();
	}

	/**
	 * Retrieve currently defined colors for displaying in the frontend.
	 *
	 * @return a map from indices to color objects they represent
	 */
	public Map<Integer, Color> retrieveAvailableColors() {
		return paletteStorage.getAvailableColors();
	}

	/**
	 * Retrieve currently defined turtle images for displaying in the frontend.
	 *
	 * @return a map from indices to images they represent
	 */
	public Map<Integer, Image> retrieveAvailableImages() { return IMAGE_MAP; }

	/**
	 * Move the currently told turtles forward when the user presses the forward arrow in the frontend.
	 *
	 * @param pixels - the number of pixels to move
	 */
	public void moveTurtlesForward(double pixels) { turtleController.moveCurrentTurtlesForward(pixels); }

	/**
	 * Move the currently told turtles backwards when the user presses the forward arrow in the frontend.
	 *
	 * @param pixels - the number of pixels to move
	 */
	public void moveTurtlesBackward(double pixels) {
		turtleController.moveCurrentTurtlesForward(-pixels);
	}

	/**
	 * Rotate the currently told turtles rightward when the user presses the forward arrow in the frontend.
	 *
	 * @param degrees - the number of degrees to rotate by
	 */
	public void turnTurtlesRight(double degrees) { turtleController.rotateCurrentTurtles(true, degrees); }

	/**
	 * Rotate the currently told turtles leftward when the user presses the forward arrow in the frontend.
	 *
	 * @param degrees - the number of degrees to rotate by
	 */
	public void turnTurtlesLeft(double degrees) { turtleController.rotateCurrentTurtles(false, degrees); }

	/**
	 * Set a turtle's pen to be down.
	 *
	 * @param index - zero based index of the turtle whose pen should be set
	 */
	public void setPenDown(int index) { turtleController.setPenDown(index + 1); }

	/**
	 * Set a turtle's pen to be up.
	 *
	 * @param index - zero based index of the turtle whose pen should be set
	 */
	public void setPenUp(int index) { turtleController.setPenUp(index + 1); }

	/**
	 * Check if a turtle's pen is down.
	 *
	 * @param index - zero based index of the turtle whose status is to be checked
	 */
	public double isPenDown(int index) {
		return turtleController.isPenDown(index + 1);
	}

	/**
	 * Retrieve the IDs of currently told turtles.
	 *
	 * @return a list of the told turtle's IDs
	 */
	public List<Integer> getToldTurtleIds() {
		return turtleController.getToldTurtleIds();
	}

	/**
	 * Create a new turtle, both its frontend and backend representation
	 */
	public void addOneTurtle() {
		int currentNumTurtles = turtleController.getNumberTurtlesCreated();
		turtleController.addTurtles(currentNumTurtles + 1);
	}

	/**
	 * Save the commands executed up to this time this is called to a file.
	 *
	 * @param fileName - the name of the file to save
	 */
	public void saveWorkspaceToFile(String fileName) {
		workspaceManager.saveWorkspaceToFile(parser, fileName);
	}

	/**
	 * Load a SLogo script that was previously saved or written manually outside the IDE.
	 *
	 * @param fileName - the name of the file to load
	 */
	public void loadWorkspaceFromFile(String fileName) throws SLogoException {
		workspaceManager.loadWorkspaceFromFile(parser, fileName);
	}

	/**
	 * Undo the last command, if possible.
	 *
	 * @throws SLogoException - if redoing old commands causes errors
	 */
	public void undo() throws SLogoException {
		if (parser.canUndo()) {
			resetView();
			parser.undo();
		}
	}

	/**
	 * Redo the last command, if possible.
	 *
	 * @throws SLogoException - if redoing old commands causes errors
	 */
	public void redo() throws SLogoException {
		if (parser.canRedo()) {
			resetView();
			parser.redo();
		}
	}

	/**
	 * Reset the project to its initial state (view and model).
	 */
	public void reset() {
		resetView();
	}

	/**
	 * Reset the view to its initial state (model preserved).
	 */
	private void resetView() {
		scopedStorage.clear();
		turtleController.clear();
	}
}