package frontend.window_setup;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import backend.Controller;
import backend.control.ScopedStorage;
import backend.error_handling.SLogoException;
import frontend.factory.ButtonFactory;
import frontend.factory.ColorPickerFactory;
import frontend.factory.MenuItemFactory;
import frontend.factory.TabFactory;
import frontend.factory.TextFieldFactory;
import frontend.turtle_display.TurtleGraphicalControls;
import frontend.turtle_display.TurtleKeyControls;
import frontend.turtle_display.TurtlePen;
import frontend.turtle_display.TurtleView;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.MenuGetter;

public class IDEWindow implements Observer {

	public static final Color STANDARD_AREA_COLOR = Color.AQUA;
	public static final double TURTLEFIELD_WIDTH = 400;
	public static final double TURTLEFIELD_HEIGHT = 400;
	public static final double LEFT_WIDTH = 150;
	public static final double LEFT_HEIGHT = TURTLEFIELD_HEIGHT;
	public static final double RIGHT_WIDTH = 400;
	public static final double RIGHT_HEIGHT = TURTLEFIELD_HEIGHT;
	public static final double TOP_WIDTH = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	public static final double TOP_HEIGHT = 100;
	public static final double BOTTOM_WIDTH = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	public static final double BOTTOM_HEIGHT = 200;
	public static final double WRAPPING_WIDTH = 100;
	public static final int OFFSET = 8;
	public static final Color STANDARD_PEN_COLOR = Color.BLACK;
	public static final String VARIABLES_HEADER = "Variables ";
	public static final String FUNCTIONS_HEADER = "Functions ";
	public static final String COLORS_HEADER = "Colors ";
	public static final String TURTLE_HEADER = "Turtle(s) Current State ";
	public static final String NEW_LINE = "\n";
	public static final double SCROLL_WIDTH = RIGHT_WIDTH - OFFSET;
	
	private static final String VARIABLE_SEPARATOR = " = ";
	private static final String PNG_FILE_EXTENSION = "*.png",
		JPG_FILE_EXTENSION = "*.jpg", GIF_FILE_EXTENSION = ".gif";
	private static final String LANGUAGE_MENU_HEADER = "Language";
	
	
	private double totalWidth = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	private double totalHeight = TOP_HEIGHT + TURTLEFIELD_HEIGHT + BOTTOM_HEIGHT;
	private Stage primaryStage;
	private Scene primaryScene;
	private HelpWindow helpWindow = new HelpWindow();
	private BorderPane borderLayout;
	private Rectangle turtleField;
	private VBox leftBox = new VBox();
	private VBox rightBox = new VBox();
	private HBox topBox = new HBox();
	private HBox bottomBox = new HBox();
	
	private Group bottomGroup = new Group();
	private Group topGroup = new Group();
	private Group leftGroup = new Group();
	private Group rightGroup = new Group();
	private Group variableGroup = new Group();
	private Group functionGroup = new Group();
	private Group colorGroup = new Group();
	private Group turtleInfoGroup = new Group();
	
	private FileChooser myChooser = makeChooser(PNG_FILE_EXTENSION, JPG_FILE_EXTENSION, GIF_FILE_EXTENSION);

	private ButtonFactory buttonMaker = new ButtonFactory();
	private TextFieldFactory textFieldMaker = new TextFieldFactory();
	private ColorPickerFactory colorPickerMaker = new ColorPickerFactory();
	private MenuItemFactory menuItemMaker = new MenuItemFactory();
	private Console console;
	private TabFactory tabMaker = new TabFactory();
	private MenuGetter menuMaker;
	
	private TurtleView turtleView;
	private ColorPicker backGroundColorPicker = new ColorPicker();
	private ColorPicker penColorPicker = new ColorPicker();

	private Controller controller;
	private ScrollPane variableScrollable;
	private GridPane variables = new GridPane();
	private ScrollPane functionScrollable;
	private GridPane functions = new GridPane();
	private GridPane turtleMovementKeys;
	private ScrollPane colorScrollable;
	private GridPane colors = new GridPane();
	private TextField strokeThickness = new TextField();
	private String[] languageList = {"Chinese","English", "French", "German", "Italian", "Portuguese", "Russian", "Spanish"};
	private GridPane turtleInfoPane;
	private ScrollPane turtleInfoScrollable;
	
	/**
	 * @param primary
	 * Constructor for IDEWindow
	 */
	public IDEWindow(Stage primary) {
		borderLayout = new BorderPane();
		borderLayout.setPrefSize(totalWidth, totalHeight);
		borderLayout.setMaxSize(totalWidth, totalHeight);
		primaryScene = new Scene(borderLayout, totalWidth, totalHeight, STANDARD_AREA_COLOR);
		turtleField = new Rectangle(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT, STANDARD_AREA_COLOR);
		turtleMovementKeys = new GridPane();
		primaryStage = primary;
		turtleInfoPane = new GridPane();
		turtleInfoScrollable = new ScrollPane();
		borderLayout.setStyle("-fx-background-color: aliceblue; -fx-text-fill: white;");

		setFormatV(leftBox,OFFSET,LEFT_WIDTH,LEFT_HEIGHT);
		
		setFormatV(rightBox,OFFSET,RIGHT_WIDTH,RIGHT_HEIGHT);
		
		setFormatH(topBox, OFFSET, TOP_WIDTH, TOP_HEIGHT, Pos.BOTTOM_CENTER);
		
		setFormatH(bottomBox, OFFSET, BOTTOM_WIDTH, BOTTOM_HEIGHT, Pos.TOP_CENTER);
		
		TabPane displayTabPane = new TabPane();
		displayTabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		formatScrollPane(variableScrollable, SCROLL_WIDTH, variables, variableGroup);
		tabMaker.makeTab(VARIABLES_HEADER,variableGroup, displayTabPane);
		
		formatScrollPane(colorScrollable, SCROLL_WIDTH, colors, colorGroup);
		tabMaker.makeTab(COLORS_HEADER,colorGroup, displayTabPane);
		
		formatScrollPane(functionScrollable, SCROLL_WIDTH, functions, functionGroup);
		tabMaker.makeTab(FUNCTIONS_HEADER,functionGroup, displayTabPane);
		
		formatScrollPane(turtleInfoScrollable, SCROLL_WIDTH, turtleInfoPane, turtleInfoGroup);
		tabMaker.makeTab(TURTLE_HEADER, turtleInfoGroup, displayTabPane);
		
		rightGroup.getChildren().add(displayTabPane);
		turtleView = new TurtleView(borderLayout, turtleField);
		turtleView.addObserver(this);
		
		ScopedStorage scopedStorage = new ScopedStorage();
		scopedStorage.addObserver(this);
		try {
			controller = new Controller(scopedStorage, turtleView, turtleField);
			console = new Console(controller);
		} catch (SLogoException buildException) {
			console.addCommand(buildException.getMessage());
		}
		formatMovementKeys(turtleMovementKeys, rightGroup, RIGHT_WIDTH);
		makeButtons();
		setBorderArrangement();
		
		TurtleKeyControls keyControls = new TurtleKeyControls(primaryScene, controller);
		keyControls.connectKeysToScene();

		ObjectProperty<Paint> backgroundProperty = turtleField.fillProperty();
		backgroundProperty.addListener((observable, oldValue, newValue)
				-> backGroundColorPicker.setValue((Color) newValue));

		penColorPicker.valueProperty().bindBidirectional(turtleView.getCurrentPenColorProperty());
		assembleTurtleInfoDisplay();
	}
	
	/**
	 * Sets up IDEWindow to display by setting the scene and calling setUpTurtleField
	 */
	public void setUpWindow() {
		primaryStage.setScene(primaryScene);
		setUpTurtleField();
	}
	
	/**
	 * Set up the starting scene with the Turtle at its starting position
	 */
	private void setUpTurtleField() {
		turtleView.displayInitialTurtle();
	}
	
	/**
	 * @param keysPane
	 * @param root
	 * @param prefSize
	 * Formats the keys pane that when clicked on moves the turtle
	 */
	private void formatMovementKeys(GridPane keysPane, Group root, double prefSize) {
		keysPane.setPrefSize(prefSize, prefSize * (2/3));
		root.getChildren().add(keysPane);
	}
	
	private void assembleTurtleInfoDisplay() {
		displayTurtleInfoHeaders();
		addInitialTurtleInfo(turtleInfoPane);
	}
	
	private void addInitialTurtleInfo(GridPane grid) {
		TurtlePen first = turtleView.getDisplayedTurtles().get(0);
		Text initialID = new Text("0");
		Text initialPos = new Text(writeCoordinatesAsPoint(convertXCoordinate(first.getXCoordinate()), convertYCoordinate(first.getYCoordinate())));
		Text initialHeading = new Text("" + first.getAngle());
		Text initialPenState = new Text(Boolean.toString(first.getIsPenDown()));
		Text initialPenColor = new Text("" + first.getPenColor());
		Text initialStrokeWidth = new Text("" + first.getStrokeWidth());
		grid.add(initialID, 0, 1);
		grid.add(initialPos, 1, 1);
		grid.add(initialHeading, 2, 1);
		grid.add(initialPenState, 3, 1);
		grid.add(initialPenColor, 4, 1);
		grid.add(initialStrokeWidth, 5, 1);
	}
	
	private String writeCoordinatesAsPoint(double xCoord, double yCoord) {
		return "(" + xCoord + ", " + yCoord + ")";
	}
	
	private double convertXCoordinate(double xCoord) {
		return xCoord - LEFT_WIDTH - TURTLEFIELD_WIDTH / 2 + TurtlePen.DEFAULT_WIDTH / 2;
	}
	
	private double convertYCoordinate(double yCoord) {
		return yCoord - TOP_HEIGHT - TURTLEFIELD_HEIGHT / 2 + TurtlePen.DEFAULT_HEIGHT / 2;
	}

	private void formatScrollPane(ScrollPane sampleScroll, double prefSize, GridPane sampleGrid, Group root) {
		sampleScroll = new ScrollPane();
		sampleGrid.setPadding(new Insets(OFFSET));
		sampleGrid.setHgap(OFFSET);
		sampleScroll.setPrefSize(prefSize,prefSize);
		sampleScroll.setContent(sampleGrid);
		root.getChildren().add(sampleScroll);
	}

	private void setFormatH(HBox hbox, int offset, double width, double height, Pos pos) {
		hbox.setPadding(new Insets(offset));
		hbox.setSpacing(offset);
		hbox.setPrefSize(width, height);
		hbox.setAlignment(pos);
	}

	private void setFormatV(VBox vbox, int offset,double leftWidth, double leftHeight) {
		vbox.setPadding(new Insets(offset));
		vbox.setSpacing(offset);
		vbox.setPrefSize(leftWidth, leftHeight);
	}

	private void setBorderArrangement() {
		borderLayout.setCenter(turtleField);
		borderLayout.setLeft(leftBox);
		borderLayout.setRight(rightBox);
		borderLayout.setTop(topBox);
		borderLayout.setBottom(console.getListView());
		borderLayout.setPrefSize(totalWidth, totalHeight);
	}
	
	private void changePenToUp() {
		List<Integer> toldTurtleIds = controller.getToldTurtleIds();
		for(int i = 0; i < toldTurtleIds.size(); i++) {
			controller.setPenUp(toldTurtleIds.get(i));
		}
	}
	
	private void changePenToDown() {
		List<Integer> toldTurtleIds = controller.getToldTurtleIds();
		for(int i = 0; i < toldTurtleIds.size(); i++) {
			controller.setPenDown(toldTurtleIds.get(i));
		}
	}

	private ImageView makeImageViewFromName(String imageName) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(imageName));
		ImageView imageNode = new ImageView(image);
		return imageNode;
	}
	
	private void makeButtons() {
		Menu languageMenu = setMenu(LANGUAGE_MENU_HEADER);
		MenuBar menuBar = new MenuBar();
		try {
			menuMaker = new MenuGetter();
			menuBar.getMenus().addAll(menuMaker.getMenuDropdowns(this));

		} catch (SLogoException e2) {
			// TODO Auto-generated catch block
			console.addError(e2.getMessage());
		}
		topGroup.getChildren().add(menuBar);
		menuBar.getMenus().add(languageMenu);
		//leftGroup.getChildren().add(menuBar);
		buttonMaker.makeGUIItem(e->helpWindow.help(), leftGroup, "Help");
		TurtleGraphicalControls graphicalControls = new TurtleGraphicalControls(controller);
		buttonMaker.makeImageGUIItemInGrid(e->graphicalControls.moveForward(), turtleMovementKeys, makeImageViewFromName("Up_Arrow.png"), 1, 0);
		buttonMaker.makeImageGUIItemInGrid(e->graphicalControls.moveBackward(), turtleMovementKeys, makeImageViewFromName("Down_Arrow.png"), 1, 1);
		buttonMaker.makeImageGUIItemInGrid(e->graphicalControls.rotateRight(), turtleMovementKeys, makeImageViewFromName("Right_Arrow.png"), 2, 1);
		buttonMaker.makeImageGUIItemInGrid(e->graphicalControls.rotateLeft(), turtleMovementKeys, makeImageViewFromName("Left_Arrow.png"), 0, 1);
		backGroundColorPicker = colorPickerMaker.makeReturnableColorPicker(e->changeBGColor(), topGroup, "BackGround Color");
		buttonMaker.makeGUIItem(e->changePenToUp(), topGroup, "Pen Up");
		buttonMaker.makeGUIItem(e->changePenToDown(), topGroup, "Pen Down");
		penColorPicker = colorPickerMaker.makeReturnableColorPicker(e->changePenColor(), topGroup, "Pen Color");
		backGroundColorPicker.setValue(STANDARD_AREA_COLOR);
		penColorPicker.setValue(STANDARD_PEN_COLOR);

		buttonMaker.makeGUIItem(e->controller.addOneTurtle(), leftGroup, "Add Turtle");
		buttonMaker.makeGUIItem(e->openFile(), leftGroup, "Set Turtle Image");
		buttonMaker.makeGUIItem(e->enterDebugging(), leftGroup, "Enter Debugging");
		strokeThickness = textFieldMaker.makeReturnableTextField(e->setPenThickness(Double.parseDouble(strokeThickness.getText())), leftGroup,"Pen Thickness");
		topBox.getChildren().addAll(topGroup.getChildren());
		bottomBox.getChildren().addAll(bottomGroup.getChildren());
		leftBox.getChildren().addAll(leftGroup.getChildren());
		rightBox.getChildren().addAll(rightGroup.getChildren());
	}
	
	private void setPenThickness(double thickness) {
		List<Integer> toldTurtleIds = controller.getToldTurtleIds();
		for(int i = 0; i < toldTurtleIds.size(); i++) {
			turtleView.changeStrokeWidth(toldTurtleIds.get(i), thickness);
		}
		strokeThickness.setText(null);
	}

	private void reset() {
		controller.reset();
	}
	
	private void enterDebugging() {
		
	}

	private void redo() {
		try {
            controller.redo();
        } catch (SLogoException badUndo) {
            console.addError(badUndo.getMessage());
        }
	}

	private void undo() {
		try {
            turtleField.setFill(STANDARD_AREA_COLOR);
            penColorPicker.setValue(STANDARD_PEN_COLOR);
            changePenColor();
            controller.undo();
        } catch (SLogoException badUndo) {
            console.addError(badUndo.getMessage());
        }
	}

	private Menu setMenu(String name)
	{
		Menu sampleMenu = new Menu(name);
		int i = 0;
		for(i = 0; i < languageList.length;i++) {
			String temp = languageList[i];
			sampleMenu.getItems().add(menuItemMaker.makeMenuItem(e->setMenuLanguage(temp), temp));
		}
		return sampleMenu;
	}
	
	private void setMenuLanguage(String language) {
		try {
			controller.setLanguage(language);
		} catch (SLogoException badLanguage) {
			console.addError(badLanguage.getMessage());
		}
	}
	
	private void changeBGColor() {
		turtleField.setFill(backGroundColorPicker.getValue());
	}
	
	private void changePenColor() {
		List<Integer> toldTurtleIds = controller.getToldTurtleIds();
		for(int i = 0; i < toldTurtleIds.size(); i++) {
			turtleView.changeDrawColor(toldTurtleIds.get(i), penColorPicker.getValue());
		}
	}
	
	private void createWindow() {
		Stage newStage = new Stage();
		newStage.setTitle("New Slogo");
		newStage.setResizable(false);
		newStage.show();
		IDEWindow window = new IDEWindow(newStage);
		window.setUpWindow();
	}
	private void saveFile() {
		File dataFile = null; 
		dataFile = myChooser.showOpenDialog(primaryStage);
		if (dataFile != null) {
			String fileLocation = dataFile.toURI().toString();
			controller.saveWorkspaceToFile(fileLocation);
		}
	}
	private void loadFile() {
		File dataFile = null; 
		dataFile = myChooser.showOpenDialog(primaryStage);
		if (dataFile != null) {
			String fileLocation = dataFile.toURI().toString();
			try {
				controller.loadWorkspaceFromFile(fileLocation);	
			} catch (SLogoException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void openFile() {
		File dataFile = null; 
		dataFile = myChooser.showOpenDialog(primaryStage);
		if (dataFile != null) {
			String fileLocation = dataFile.toURI().toString();
			List<Integer> toldTurtleIds = controller.getToldTurtleIds();
			for(int i = 0; i < toldTurtleIds.size(); i++) {
				turtleView.changeImage(toldTurtleIds.get(i), new Image(fileLocation));
			}
		}
	}

	/**
	 * @param extensionAccepted
	 * @return This method makes the FileChooser object that allows users to open an
	 *         XML File.
	 */
	private FileChooser makeChooser(String... extensionsAccepted) {
		FileChooser result = new FileChooser();
		result.setTitle("open");
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		//result.getExtensionFilters().setAll(new ExtensionFilter("Image Files", extensionsAccepted));
		return result;
	}
	
	public Rectangle getTurtleField() {
		return turtleField;
	}
	
	public Pane getPane() {
		return borderLayout;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof TurtleView) {
			updateTurtleInfo();
		}
		else {
			updateVariableDisplay();
			updateFunctionsDisplay();
			updateColorDisplay();
		}
	}
	
	private void displayTurtleInfoHeaders() {
		Text idHeader = new Text("ID");
		Text posHeader = new Text("Position");
		Text headingHeader = new Text("Heading");
		Text penStateHeader = new Text("Pen State");
		Text penColorHeader = new Text("Pen Color");
		Text penThicknessHeader = new Text("Stroke Width");
		turtleInfoPane.add(idHeader, 0, 0);
		turtleInfoPane.add(posHeader, 1, 0);
		turtleInfoPane.add(headingHeader, 2, 0);
		turtleInfoPane.add(penStateHeader, 3, 0);
		turtleInfoPane.add(penColorHeader, 4, 0);
		turtleInfoPane.add(penThicknessHeader, 5, 0);
	}
	
	private void updateTurtleInfo() {
		List<TurtlePen> currentTurtles = turtleView.getDisplayedTurtles();
		turtleInfoPane.getChildren().clear();
		displayTurtleInfoHeaders();
		for(int i = 0; i < currentTurtles.size(); i++) {
			TurtlePen current = turtleView.getDisplayedTurtles().get(i);
			Text currentID = new Text("" + i);
			Text currentPos = new Text(writeCoordinatesAsPoint(convertXCoordinate(current.getXCoordinate()), convertYCoordinate(current.getYCoordinate())));
			Text currentHeading = new Text("" + current.getAngle());
			Text currentPenState = new Text(Boolean.toString(current.getIsPenDown()));
			Text currentPenColor = new Text("" + current.getPenColor());
			Text currentStrokeWidth = new Text("" + current.getStrokeWidth());
			turtleInfoPane.add(currentID, 0, i + 1);
			turtleInfoPane.add(currentPos, 1, i + 1);
			turtleInfoPane.add(currentHeading, 2, i + 1);
			turtleInfoPane.add(currentPenState, 3, i + 1);
			turtleInfoPane.add(currentPenColor, 4, i + 1);
			turtleInfoPane.add(currentStrokeWidth, 5, i + 1);
		}
	}

	private void updateVariableDisplay() {
		Map<String, Double> availableVariables = controller.retrieveAvailableVariables();
		int variableCount = 0;
		variables.getChildren().clear();
		for (String variableName : availableVariables.keySet()) {
			Text newVariable = new Text(variableName+ VARIABLE_SEPARATOR + availableVariables.get(variableName));
			newVariable.setWrappingWidth(WRAPPING_WIDTH);
			newVariable.setOnMouseClicked(e->changeVariables(variableName));
			variableCount++;
			variables.add(newVariable, 0, variableCount);
		}
	}
	
	private void updateColorDisplay() {
		Map<Double, Color> availableColors = controller.retrieveAvailableColors();
		int colorCount = 0;
		colors.getChildren().clear();
		for (Double colorNumber : availableColors.keySet()) {
			Text newColor = new Text(COLORS_HEADER +colorNumber+ VARIABLE_SEPARATOR);
			newColor.setWrappingWidth(WRAPPING_WIDTH);
			Rectangle colorBlock = new Rectangle(20,20,availableColors.get(colorNumber));
			HBox hbox = new HBox();
			hbox.getChildren().addAll(newColor,colorBlock);
			colorCount++;
			colors.add(hbox, 0, colorCount);
		}
	}

	// TODO - need to change to a separate variables display not just functions display;
	private void updateFunctionsDisplay() {
		Map<String, List<String>> availableFunctions = controller.retrieveDefinedFunctions();
		int functionCount = 0;
		functions.getChildren().clear();
		for (String functionName : availableFunctions.keySet()) {
			Text newFunction = new Text(functionName+ VARIABLE_SEPARATOR + availableFunctions.get(functionName));
			newFunction.setWrappingWidth(WRAPPING_WIDTH);
			newFunction.setOnMouseClicked(e->callFunctions(functionName));
			functionCount++;
			functions.add(newFunction, 0, functionCount);
		}
	}
	private void changeVariables(String variableName) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Variable");
		dialog.setHeaderText("Please Enter New Value for "+variableName);
		dialog.setContentText("Enter New Value:");
		Optional<String> result = dialog.showAndWait();
		
		result.ifPresent(name -> controller.updateVariable(variableName,Double.parseDouble(name)));
		
	}
	
	private void callFunctions(String functionName) {
		console.setCommandEntry(functionName);
	}
}
