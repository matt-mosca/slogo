package frontend.window_setup;

import java.io.File;

import backend.Parser;
import backend.error_handling.SLogoException;
import frontend.factory.ButtonFactory;
import frontend.factory.ColorPickerFactory;
import frontend.factory.TextAreaFactory;
import frontend.factory.TextFieldFactory;
import frontend.turtle_display.TurtleView;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class IDEWindow {
	private static Paint STANDARD_AREA_COLOR = Color.BLUE;
	private static Paint penColor = Color.BLACK;
	public static final double TURTLEFIELD_WIDTH = 400;
	public static final double TURTLEFIELD_HEIGHT = 400;
	public static final double TURTLEFIELD_DEPTH = 0;
	public static final double LEFT_WIDTH = 150;
	public static final double LEFT_HEIGHT = TURTLEFIELD_HEIGHT;
	public static final double RIGHT_WIDTH = 150;
	public static final double RIGHT_HEIGHT = TURTLEFIELD_HEIGHT;
	public static final double TOP_WIDTH = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	public static final double TOP_HEIGHT = 100;
	public static final double BOTTOM_WIDTH = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	public static final double BOTTOM_HEIGHT = 200;
	public static final double WRAPPING_WIDTH = 100;
	
	private Stage primaryStage;
	private Scene primaryScene;
	private BorderPane borderLayout;
	private Rectangle turtleField;
//	private Box turtleRegion;
	private HBox turtleRegion;
	private VBox leftBox;
	private VBox rightBox;
	private HBox topBox;
	private HBox bottomBox;
	private TextArea commandTextArea;
	//private TextField bGColorTextField;
	private TextField penColorTextField;
	private double totalWidth = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	private double totalHeight = TOP_HEIGHT + TURTLEFIELD_HEIGHT + BOTTOM_HEIGHT;
	private boolean isError = false;
	
	private Stage helpStage = new Stage();


	private Group bottomGroup = new Group();
	private Group topGroup = new Group();
	private Group leftGroup = new Group();
	private Group rightGroup = new Group();
	private GridPane console = new GridPane();
	
	public static final int OFFSET = 8;

	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final String DATA_FILE_EXTENSION = "*.jpg";

	ButtonFactory buttonMaker = new ButtonFactory();
	ColorPickerFactory colorPickerMaker = new ColorPickerFactory();
	TextFieldFactory textFieldMaker = new TextFieldFactory();
	TextAreaFactory textAreaMaker = new TextAreaFactory();
	Parser commandParser = new Parser();
	private Image turtlePic;
	private int commandCount = 0;
	
	private ColorPicker backGroundColorPicker = new ColorPicker();
	private ColorPicker penColorPicker = new ColorPicker();
	
	public IDEWindow() {
		borderLayout = new BorderPane();
		borderLayout.setPrefSize(totalWidth, totalHeight);
		borderLayout.setMaxSize(totalWidth, totalHeight);
		primaryScene = new Scene(borderLayout, totalWidth, totalHeight, STANDARD_AREA_COLOR);
		turtleField = new Rectangle(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT, STANDARD_AREA_COLOR);
//		turtleField = new Rectangle();
//		turtleField.setFill(STANDARD_AREA_COLOR);
//		turtleRegion = new Box(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT, TURTLEFIELD_DEPTH);
		turtleRegion = new HBox();
		turtleRegion.setPrefSize(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT);
		turtleRegion.setStyle("-fx-background-color: white;"); //do not use arbitrary white here, make constant
		leftBox = new VBox();
		leftBox.setPadding(new Insets(OFFSET));
		leftBox.setSpacing(OFFSET);
		leftBox.setPrefSize(LEFT_WIDTH, LEFT_HEIGHT);
		rightBox = new VBox();
		rightBox.setPadding(new Insets(OFFSET));
		rightBox.setSpacing(OFFSET);
		rightBox.setPrefSize(RIGHT_WIDTH, RIGHT_HEIGHT);
		topBox = new HBox();
		topBox.setPadding(new Insets(OFFSET));
		topBox.setSpacing(OFFSET);
		topBox.setPrefSize(TOP_WIDTH, TOP_HEIGHT);
		bottomBox = new HBox();
		bottomBox.setPadding(new Insets(OFFSET));
		bottomBox.setSpacing(OFFSET);
		bottomBox.setPrefSize(BOTTOM_WIDTH, BOTTOM_HEIGHT);
		console.setAlignment(Pos.CENTER);
		console.setHgap(10);
		console.setVgap(2);
		console.setPadding(new Insets(25, 25, 25, 25));
		Text Console = new Text("Command History: ");
		console.add(Console, 0, commandCount);
		rightGroup.getChildren().add(console);
		
		//bottomBox.setPrefSize(BOTTOM_WIDTH, BOTTOM_HEIGHT);
		
		makeButtons(primaryStage);
		
		borderLayout.setCenter(turtleField);
//		borderLayout.setCenter(turtleRegion);
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
		TurtleView field = new TurtleView(borderLayout, turtleField, turtleRegion);
		field.displayInitialTurtle();
	}
	
	private void makeButtons(Stage s) {
		Text enterCommand = new Text("Enter Command");
		leftGroup.getChildren().add(enterCommand);
		buttonMaker.makeGUIItem(e->openFile(s), topGroup, "Set Turtle Image");
		buttonMaker.makeGUIItem(e->help(), bottomGroup, "Help");
		//commandTextField = textFieldMaker.makeReturnableTextField(e->storeCommand(), leftGroup, "Command");
		commandTextArea = textAreaMaker.makeReturnableTextArea(null, leftGroup, null);
		buttonMaker.makeGUIItem(e->enterCommand(), leftGroup, "Enter Command");
		colorPickerMaker.makeGUIItem(e->changeBGColor(), topGroup, "BackGround Color");
		colorPickerMaker.makeGUIItem(e->changePenColor(), topGroup, "Pen Color");
		
		topBox.getChildren().addAll(topGroup.getChildren());
		bottomBox.getChildren().addAll(bottomGroup.getChildren());
		leftBox.getChildren().addAll(leftGroup.getChildren());
		rightBox.getChildren().addAll(rightGroup.getChildren());
	}
	
	private void changeBGColor() {
		STANDARD_AREA_COLOR = backGroundColorPicker.getValue();
	}
	
	private void changePenColor() {
		penColor = penColorPicker.getValue();
	}
	
	private void enterCommand() {
		Text history = new Text();
		String commandInput = commandTextArea.getText();
		commandCount++;
		try {
			if(commandParser.validateCommand(commandInput))
			{
				commandParser.executeCommand(commandInput);
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
			turtlePic = new Image(fileLocation);  
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
	
}

