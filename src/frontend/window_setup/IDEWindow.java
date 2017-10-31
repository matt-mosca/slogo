package frontend.window_setup;

import backend.Controller;
import backend.control.ScopedStorage;
import backend.error_handling.SLogoException;
import frontend.factory.ButtonFactory;
import frontend.factory.ColorPickerFactory;
import frontend.factory.MenuItemFactory;
import frontend.factory.TabFactory;
import frontend.factory.TextAreaFactory;
import frontend.turtle_display.TurtleGraphicalControls;
import frontend.turtle_display.TurtleKeyControls;
import frontend.turtle_display.TurtlePen;
import frontend.turtle_display.TurtleView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class IDEWindow implements Observer {
	public static final Color STANDARD_AREA_COLOR = Color.AQUA;
	public static final double TURTLEFIELD_WIDTH = 400;
	public static final double TURTLEFIELD_HEIGHT = 400;
	public static final double LEFT_WIDTH = 150;
	public static final double LEFT_HEIGHT = TURTLEFIELD_HEIGHT;
	public static final double RIGHT_WIDTH = 150;
	public static final double RIGHT_HEIGHT = TURTLEFIELD_HEIGHT;
	public static final double TOP_WIDTH = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	public static final double TOP_HEIGHT = 100;
	public static final double BOTTOM_WIDTH = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	public static final double BOTTOM_HEIGHT = 200;
	public static final double WRAPPING_WIDTH = 100;
	public static final int OFFSET = 8;
	public static final Color STANDARD_PEN_COLOR = Color.BLACK;
	private double totalWidth = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	private double totalHeight = TOP_HEIGHT + TURTLEFIELD_HEIGHT + BOTTOM_HEIGHT;
	
	private static final String VARIABLE_SEPARATOR = " = ";
	public static final String VARIABLES_HEADER = "Variables ";
	public static final String FUNCTIONS_HEADER = "Functions ";
	public static final String COLORS_HEADER = "Colors ";
	public static final String NEW_LINE = "\n";
	private static final String PNG_FILE_EXTENSION = "*.png",
		JPG_FILE_EXTENSION = "*.jpg", GIF_FILE_EXTENSION = ".gif";
	private static final String LANGUAGE_MENU_HEADER = "Language";


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
	
	private FileChooser myChooser = makeChooser(PNG_FILE_EXTENSION, JPG_FILE_EXTENSION, GIF_FILE_EXTENSION);

	private ButtonFactory buttonMaker = new ButtonFactory();
	private ColorPickerFactory colorPickerMaker = new ColorPickerFactory();
	private MenuItemFactory menuItemMaker = new MenuItemFactory();
	private Console console;
	private TabFactory tabMaker = new TabFactory();
	
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
	private String[] languageList = {"Chinese","English","French", "German", "Italian", "Portuguese", "Russian", "Spanish"};
	
	
	public IDEWindow(Stage primary) {
		borderLayout = new BorderPane();
		borderLayout.setPrefSize(totalWidth, totalHeight);
		borderLayout.setMaxSize(totalWidth, totalHeight);
		primaryScene = new Scene(borderLayout, totalWidth, totalHeight, STANDARD_AREA_COLOR);
		turtleField = new Rectangle(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT, STANDARD_AREA_COLOR);
		turtleMovementKeys = new GridPane();
		primaryStage = primary;
		
		setFormatV(leftBox,OFFSET,LEFT_WIDTH,LEFT_HEIGHT);
		
		setFormatV(rightBox,OFFSET,RIGHT_WIDTH,RIGHT_HEIGHT);
		
		setFormatH(topBox, OFFSET, TOP_WIDTH, TOP_HEIGHT, Pos.BOTTOM_CENTER);
		
		setFormatH(bottomBox, OFFSET, BOTTOM_WIDTH, BOTTOM_HEIGHT, Pos.TOP_CENTER);
		
		TabPane tabPane = new TabPane();
		
		formatScrollPane(variableScrollable, 150, variables, variableGroup);
		tabMaker.makeTab(VARIABLES_HEADER,variableGroup, tabPane);
		
		formatScrollPane(colorScrollable, 150, colors, colorGroup);
		tabMaker.makeTab(COLORS_HEADER,colorGroup, tabPane);
		
		formatScrollPane(functionScrollable, 150, functions, functionGroup);
		tabMaker.makeTab(FUNCTIONS_HEADER,functionGroup, tabPane);
		
		rightGroup.getChildren().add(tabPane);
		turtleView = new TurtleView(borderLayout, turtleField);

		ScopedStorage scopedStorage = new ScopedStorage();
		scopedStorage.addObserver(this);
		controller = new Controller(scopedStorage, turtleView, turtleField);
		console = new Console(controller);
		formatMovementKeys(turtleMovementKeys, rightGroup, RIGHT_WIDTH);
		makeButtons(primaryStage);
		setBorderArrangement();
		
		TurtleKeyControls keyControls = new TurtleKeyControls(primaryScene, controller);
		keyControls.connectKeysToScene();

		ObjectProperty<Paint> backgroundProperty = turtleField.fillProperty();
		backgroundProperty.addListener((observable, oldValue, newValue)
				-> backGroundColorPicker.setValue((Color) newValue));

		penColorPicker.valueProperty().bindBidirectional(turtleView.getCurrentPenColorProperty());
	}
	
	public void setUpWindow() {
		primaryStage.setScene(primaryScene);
		setUpTurtleField();
	}
	
	private void setUpTurtleField() {
		turtleView.displayInitialTurtle();
	}
	
	private void formatMovementKeys(GridPane keysPane, Group root, double prefSize) {
		keysPane.setPrefSize(prefSize, prefSize * (2/3));
		root.getChildren().add(keysPane);
	}

	private void formatScrollPane(ScrollPane sampleScroll, int prefSize, GridPane sampleGrid, Group root) {
		sampleScroll = new ScrollPane();
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
	
	private void makeButtons(Stage s) {
		Menu languageMenu = setMenu(LANGUAGE_MENU_HEADER);
		MenuBar languageMenuBar = new MenuBar();
		languageMenuBar.getMenus().add(languageMenu);
		buttonMaker.makeGUIItem(e->helpWindow.help(), leftGroup, "Help");
		buttonMaker.makeGUIItem(e->createWindow(), topGroup, "Create New Window");
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

		// UNDO/REDO TESTING
		Button undo = new Button("Undo");
		undo.setOnAction(e -> {
			try {
				turtleField.setFill(STANDARD_AREA_COLOR);
				penColorPicker.setValue(STANDARD_PEN_COLOR);
				changePenColor();
				controller.undo();
			} catch (SLogoException badUndo) {
				console.addError(badUndo.getMessage());
			}
		});
		Button redo = new Button("Redo");
		redo.setOnAction(e -> {
			try {
				controller.redo();
			} catch (SLogoException badUndo) {
				console.addError(badUndo.getMessage());
			}
		});
		Button reset = new Button("Reset");
		reset.setOnAction(e -> controller.reset());
		
		leftGroup.getChildren().addAll(languageMenuBar, undo, redo, reset);
		buttonMaker.makeGUIItem(e->controller.addOneTurtle(), leftGroup, "Add Turtle");
		buttonMaker.makeGUIItem(e->openFile(s), leftGroup, "Set Turtle Image");
		//leftGroup.getChildren().add(new Rectangle(50,50));
		topBox.getChildren().addAll(topGroup.getChildren());
		bottomBox.getChildren().addAll(bottomGroup.getChildren());
		leftBox.getChildren().addAll(leftGroup.getChildren());
		rightBox.getChildren().addAll(rightGroup.getChildren());
	}
	
	private Menu setMenu(String name)
	{
		Menu sampleMenu = new Menu(name);
		int i = 0;
		for(i = 0; i<languageList.length;i++) {
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
	
	private void createWindow()
	{
		Stage newStage = new Stage();
		newStage.setTitle("New Slogo");
		newStage.setResizable(false);
		newStage.show();
		IDEWindow window = new IDEWindow(newStage);
		window.setUpWindow();
	}
	
	private void openFile(Stage s) {
		File dataFile = null; 
		dataFile = myChooser.showOpenDialog(s);
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
		result.getExtensionFilters().setAll(new ExtensionFilter("Image Files", extensionsAccepted));
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
		updateVariableDisplay();
		updateFunctionsDisplay();
		updateColorDisplay();

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
			hbox.setOnMouseClicked(e->changeColors(colorNumber));
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
			newFunction.setOnMouseClicked(e->changeFunctions(functionName));
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
		
		result.ifPresent(name -> System.out.println("Your name: " + name));
		
	}
	
	private void changeFunctions(String functionName) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Function");
		dialog.setHeaderText("Please Enter New Value for "+functionName);
		dialog.setContentText("Enter New Value:");
		Optional<String> result = dialog.showAndWait();
		
		result.ifPresent(name -> System.out.println("Your name: " + name));
	}
	private void changeColors(Double colorNumber) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Change Color");
		dialog.setHeaderText("Please Enter New Value for Color "+colorNumber);
		dialog.setContentText("Enter New Value:");
		Optional<String> result = dialog.showAndWait();
		
		result.ifPresent(name -> System.out.println("Your name: " + name));
	}
}
