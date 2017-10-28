package frontend.window_setup;

import backend.Controller;
import backend.control.ScopedStorage;
import backend.error_handling.SLogoException;
import frontend.factory.ButtonFactory;
import frontend.factory.ColorPickerFactory;
import frontend.factory.MenuItemFactory;
import frontend.factory.TextAreaFactory;
import frontend.factory.TextFieldFactory;
import frontend.turtle_display.Drawer;
import frontend.turtle_display.TurtlePen;
import frontend.turtle_display.TurtleView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
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

public class IDEWindow implements Observer {
	private static final Paint STANDARD_AREA_COLOR = Color.AQUA;
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
	private static final String VARIABLE_SEPARATOR = " = ";
	public static final String VARIABLES_HEADER = "Variables: ";
	public static final String NEW_LINE = "\n";

	private Stage primaryStage;
	private Scene primaryScene;
	private BorderPane borderLayout;
	private Rectangle turtleField;
	private VBox leftBox = new VBox();
	private VBox rightBox = new VBox();
	private HBox topBox = new HBox();
	private HBox bottomBox = new HBox();
	private TextArea commandTextArea;
	private double totalWidth = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	private double totalHeight = TOP_HEIGHT + TURTLEFIELD_HEIGHT + BOTTOM_HEIGHT;
		
	private Stage helpStage = new Stage();
	
	private Group bottomGroup = new Group();
	private Group topGroup = new Group();
	private Group leftGroup = new Group();
	private Group rightGroup = new Group();
	
	private GridPane console = new GridPane();
	
	private MenuItem chinese = new MenuItem();
	private MenuItem english = new MenuItem();
	private MenuItem french = new MenuItem();
	private MenuItem german = new MenuItem();
	private MenuItem italian = new MenuItem();
	private MenuItem portuguese = new MenuItem();
	private MenuItem russian = new MenuItem();
	private MenuItem spanish = new MenuItem();

	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final String DATA_FILE_EXTENSION = "*.jpg";

	ButtonFactory buttonMaker = new ButtonFactory();
	ColorPickerFactory colorPickerMaker = new ColorPickerFactory();
	TextFieldFactory textFieldMaker = new TextFieldFactory();
	TextAreaFactory textAreaMaker = new TextAreaFactory();
	private TurtleView turtleView;
	private Image turtlePic;
	private int commandCount = 0;
	
	private ColorPicker backGroundColorPicker = new ColorPicker();
	private ColorPicker penColorPicker = new ColorPicker();
	private MenuItemFactory menuItemMaker = new MenuItemFactory();
	private Drawer drawer = new Drawer();
	private TurtlePen turtlePen = new TurtlePen(totalHeight, totalHeight);
	private Text variableDisplay;
	private Controller controller;
	private ScrollPane consoleScrollable;
	ScrollPane variableScrollable;
	private GridPane variables = new GridPane();
	private int variableCount = 0;
	
	public IDEWindow() {
		borderLayout = new BorderPane();
		borderLayout.setPrefSize(totalWidth, totalHeight);
		borderLayout.setMaxSize(totalWidth, totalHeight);
		primaryScene = new Scene(borderLayout, totalWidth, totalHeight, STANDARD_AREA_COLOR);
		turtleField = new Rectangle(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT, STANDARD_AREA_COLOR);
		
		
		setFormatV(leftBox,OFFSET,LEFT_WIDTH,LEFT_HEIGHT);
		
		setFormatV(rightBox,OFFSET,RIGHT_WIDTH,RIGHT_HEIGHT);
		
		setFormatH(topBox, OFFSET, TOP_WIDTH, TOP_HEIGHT, Pos.BOTTOM_CENTER);
		
		setFormatH(bottomBox, OFFSET, BOTTOM_WIDTH, BOTTOM_HEIGHT, Pos.TOP_CENTER);
		
		//console.setAlignment(Pos.CENTER);
		console.setHgap(10);
		console.setVgap(2);
		//console.setPadding(new Insets(25, 25, 25, 25));
		Text consoleLabel = new Text("Command History: ");
		console.add(consoleLabel, 0, commandCount);
		
		variableDisplay = new Text(VARIABLES_HEADER);
		variables.add(variableDisplay, 0, variableCount);
		
		formatScrollPane(consoleScrollable, 150, console, rightGroup);
		formatScrollPane(variableScrollable, 150, variables, rightGroup);
	
		
		//bottomBox.setPrefSize(BOTTOM_WIDTH, BOTTOM_HEIGHT);
		
		makeButtons(primaryStage);
		setBorderArrangement();

		turtleView = new TurtleView(borderLayout, turtleField);

		ScopedStorage scopedStorage = new ScopedStorage();
		scopedStorage.addObserver(this);
		controller = new Controller(scopedStorage, turtleView, turtleField);
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
		borderLayout.setBottom(bottomBox);
		borderLayout.setPrefSize(totalWidth, totalHeight);
	}
	
	public void setUpWindow(Stage primary) {
		primary.setScene(primaryScene);
		setUpTurtleField();
	}
	
	private void setUpTurtleField() {
		turtleView.displayInitialTurtle();
	}
	
	private void makeButtons(Stage s) {
		Text enterCommand = new Text("Enter Command:");
		leftGroup.getChildren().add(enterCommand);
		buttonMaker.makeGUIItem(e->openFile(s), topGroup, "Set Turtle Image");
		buttonMaker.makeGUIItem(e->help(), bottomGroup, "Help");
		//commandTextField = textFieldMaker.makeReturnableTextField(e->storeCommand(), leftGroup, "Command");
		commandTextArea = textAreaMaker.makeReturnableTextArea(null, leftGroup, null);
		buttonMaker.makeGUIItem(e->enterCommand(), leftGroup, "Enter Command");
		backGroundColorPicker = colorPickerMaker.makeReturnableColorPicker(e->changeBGColor(), topGroup, "BackGround Color");
		penColorPicker = colorPickerMaker.makeReturnableColorPicker(e->changePenColor(), topGroup, "Pen Color");
		backGroundColorPicker.setValue((Color) STANDARD_AREA_COLOR);
		penColorPicker.setValue(Color.BLACK);
		
		chinese = menuItemMaker.makeMenuItem(e->{
				setMenuLanguage("Chinese");
		}, "Chinese");
		
		english = menuItemMaker.makeMenuItem(e->{
				setMenuLanguage("English");
		}, "English");
		
		french = menuItemMaker.makeMenuItem(e->{
				setMenuLanguage("French");
		}, "French");
		
		german = menuItemMaker.makeMenuItem(e->{
			setMenuLanguage("German");
		}, "German");
		
		italian = menuItemMaker.makeMenuItem(e->{
				setMenuLanguage("Italian");
		}, "Italian");
		
		portuguese = menuItemMaker.makeMenuItem(e->{
				setMenuLanguage("Portuguese");
		}, "Portuguese");
		
		russian = menuItemMaker.makeMenuItem(e->{
				setMenuLanguage("Russian");
		}, "Russian");
		
		spanish = menuItemMaker.makeMenuItem(e->{
				setMenuLanguage("Spanish");
		}, "Spanish");
		
		Menu languageMenu = new Menu("Language");
		languageMenu.getItems().add(chinese);
		languageMenu.getItems().add(english);
		languageMenu.getItems().add(french);
		languageMenu.getItems().add(german);
		languageMenu.getItems().add(italian);
		languageMenu.getItems().add(portuguese);
		languageMenu.getItems().add(russian);
		languageMenu.getItems().add(spanish);
		MenuBar languageMenuBar = new MenuBar();
		languageMenuBar.getMenus().add(languageMenu);
		
		bottomGroup.getChildren().add(languageMenuBar);
		
		topBox.getChildren().addAll(topGroup.getChildren());
		bottomBox.getChildren().addAll(bottomGroup.getChildren());
		leftBox.getChildren().addAll(leftGroup.getChildren());
		rightBox.getChildren().addAll(rightGroup.getChildren());
	}
	
	private void setMenuLanguage(String language) {
		try {
			controller.setLanguage(language);
		} catch (SLogoException badLanguage) {
			Text errorMessage = new Text();
			errorMessage.setText(commandCount+". "+badLanguage.getMessage());
			errorMessage.setFill(Color.RED);
			console.add(errorMessage, 0, ++commandCount);
		}
	}
	
	private void changeBGColor() {
		turtleField.setFill(backGroundColorPicker.getValue());
	}
	
	private void changePenColor() {
		turtleView.changeDrawColor(penColorPicker.getValue());
		System.out.println(penColorPicker.getValue());
	}
	
	private void enterCommand() {
		Text history = new Text();
		String commandInput = commandTextArea.getText();
		commandCount++;
		try {
			if(controller.validateCommand(commandInput)){
				controller.executeCommand(commandInput);
			}
			history.setText(commandCount+". "+commandInput);
		}
		catch(SLogoException e) {
			
			history.setText(commandCount+". "+e.getMessage());
			history.setFill(Color.RED);
		}
		history.setWrappingWidth(WRAPPING_WIDTH);
		console.add(history, 0, commandCount);
		commandTextArea.setText("");
	}
	
	private void openFile(Stage s) {
		File dataFile = null; 
		dataFile = myChooser.showOpenDialog(s);
		if (dataFile != null) {
			String fileLocation = dataFile.toURI().toString();
			turtlePen.setImage(new Image(fileLocation));  
			turtleView.showTurtle(turtlePen);
		}
	}
	/**
	 * @param extensionAccepted
	 * @return This method makes the FileChooser object that allows users to open an
	 *         XML File.
	 */
	private FileChooser makeChooser(String extensionAccepted) {
		FileChooser result = new FileChooser();
		result.setTitle("open");
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
	}

	private void help() {
		Text t = new Text();
		t.setFont(new Font(20));
		t.setWrappingWidth(200);
		t.setTextAlignment(TextAlignment.JUSTIFY);
		t.setText("Commands/help");

		VBox vbox = new VBox();
		vbox.getChildren().add(t);
		Scene scene = new Scene(vbox, 500, 150, Color.WHITE);
		helpStage.setTitle("Help");
		helpStage.setScene(scene);
		helpStage.show();
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

	}

	private void updateVariableDisplay() {
		Text newVariable = new Text();
		Map<String, Double> availableVariables = controller.retrieveAvailableVariables();
		System.out.println(availableVariables);
		StringBuilder variablesBuffer = new StringBuilder(VARIABLES_HEADER);
		for (String variableName : availableVariables.keySet()) {
			variablesBuffer.append(NEW_LINE + variableName
					+ VARIABLE_SEPARATOR + availableVariables.get(variableName));
		}
		newVariable.setWrappingWidth(WRAPPING_WIDTH);
		newVariable.setText(variablesBuffer.toString());
		variables.add(newVariable, 0, variableCount);
	}

	// TODO - need to change to a separate variables display not just functions display;
	private void updateFunctionsDisplay() {
		Text newVariable = new Text();
		Map<String, List<String>> availableVariables = controller.retrieveDefinedFunctions();
		StringBuilder variablesBuffer = new StringBuilder();
		for (String variableName : availableVariables.keySet()) {
			variablesBuffer.append(NEW_LINE + variableName + availableVariables.get(variableName));
		}
		// TODO - change below
		newVariable.setWrappingWidth(WRAPPING_WIDTH);
		newVariable.setText(variablesBuffer.toString());
		variables.add(newVariable, 0, variableCount);
	}

}
