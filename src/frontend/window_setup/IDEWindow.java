package frontend.window_setup;

import java.io.File;

import apis.ButtonFactory;
import apis.TextAreaFactory;
import apis.TextFieldFactory;
import frontend.turtle_display.TurtleView;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class IDEWindow {
	public static final Paint STANDARD_AREA_COLOR = Color.WHITE;
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
	
	private Stage primaryStage;
	private Scene primaryScene;
	private BorderPane borderLayout;
	private Rectangle turtleField;
	private GridPane turtleGrid;
	private VBox leftBox;
	private VBox rightBox;
	private HBox topBox;
	private HBox bottomBox;
	private TextArea commandTextArea;
	private TextField bGColorTextField;
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
	private static final Color BACKGROUND = Color.BLACK;
	private static final String TITLE = "SLogo";
	private Group splash = new Group();
	private Group helpText = new Group();
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final String DATA_FILE_EXTENSION = "*.jpg";
	private static final int VERT_SIZE = 650;
	private static final int HORIZONTAL_SIZE = 575;
	ButtonFactory buttonMaker = new ButtonFactory();
	TextFieldFactory textFieldMaker = new TextFieldFactory();
	TextAreaFactory textAreaMaker = new TextAreaFactory();
	private Image turtlePic;
	private int commandCount = 0;
	private String errorMessage;
	
	public IDEWindow() {
		borderLayout = new BorderPane();
		primaryScene = new Scene(borderLayout, totalWidth, totalHeight, STANDARD_AREA_COLOR);
		turtleField = new Rectangle(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT, STANDARD_AREA_COLOR);
//		turtleGrid = new GridPane();
//		turtleGrid.setPrefSize(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT);
		leftBox = new VBox();
		leftBox.setPadding(new Insets(OFFSET));
		leftBox.setSpacing(OFFSET);
//		left.setPrefSize(LEFT_WIDTH, LEFT_HEIGHT);
		rightBox = new VBox();
		rightBox.setPadding(new Insets(OFFSET));
		rightBox.setSpacing(OFFSET);
//		right.setPrefSize(RIGHT_WIDTH, RIGHT_HEIGHT);
		topBox = new HBox();
		topBox.setPadding(new Insets(OFFSET));
		topBox.setSpacing(OFFSET);
		
		bottomBox = new HBox();
		bottomBox.setPadding(new Insets(OFFSET));
		bottomBox.setSpacing(OFFSET);
		
		console.setAlignment(Pos.CENTER);
		console.setHgap(10);
		//console.setVgap(2);
		console.setPadding(new Insets(25, 25, 25, 25));
		Text Console = new Text("Command History: ");
		console.add(Console, 0, commandCount);
		rightGroup.getChildren().add(console);
		
		//bottomBox.setPrefSize(BOTTOM_WIDTH, BOTTOM_HEIGHT);
		
		makeButtons(primaryStage);
		
		borderLayout.setCenter(turtleField);
		borderLayout.setLeft(leftBox);
		borderLayout.setRight(rightBox);
		borderLayout.setTop(topBox);
		borderLayout.setBottom(bottomBox);
	}
	
	public void setUpWindow(Stage primary) {
		primary.setScene(primaryScene);
		setUpTurtleField();
	}
	
	private void setUpTurtleField() {
		TurtleView field = new TurtleView(borderLayout, turtleField);
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
		bGColorTextField = textFieldMaker.makeReturnableTextField(e->changeBGColor(), topGroup, "BackGround Color");
		penColorTextField = textFieldMaker.makeReturnableTextField(e->changePenColor(), topGroup, "Pen Color");	
		topBox.getChildren().addAll(topGroup.getChildren());
		bottomBox.getChildren().addAll(bottomGroup.getChildren());
		leftBox.getChildren().addAll(leftGroup.getChildren());
		rightBox.getChildren().addAll(rightGroup.getChildren());
	}
	
	private void changeBGColor() {
		bGColorTextField.getText();
	}
	
	private void enterCommand() {
		Text history = new Text(commandTextArea.getText()+"\n");
		System.out.println(commandTextArea.getText());
		commandCount++;
		if(!isError) {
			console.add(history, 0, commandCount);
			System.out.println(commandTextArea.getText());
		}
		else {
			history.setText(errorMessage);
			history.setFill(Color.RED);
			console.add(history, 0, commandCount);
			isError = false;
		}
		commandTextArea.setText("");
	}
	
	private void changePenColor() {
		penColorTextField.getText();
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
	
	public void takeError(String error) {
		isError = true;
		errorMessage = error;
	}
	
	public Rectangle getTurtleField() {
		return turtleField;
	}
	
	public Pane getPane() {
		return borderLayout;
	}
	
}

