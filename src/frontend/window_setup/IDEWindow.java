package frontend.window_setup;

import java.io.File;

import apis.ButtonFactory;
import apis.TextFieldFactory;
import frontend.turtle_display.TurtleView;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
	private TextField commandTextField; //Need to make right panel
	private TextField bGColorTextField;
	private TextField penColorTextField;
	private double totalWidth = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	private double totalHeight = TOP_HEIGHT + TURTLEFIELD_HEIGHT + BOTTOM_HEIGHT;
	
	private Stage helpStage = new Stage();
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private Timeline animation = new Timeline();
	private Group bottomGroup = new Group();
	private Group topGroup = new Group();
	private Group leftGroup = new Group();
	private Group rightGroup = new Group();
	private GridPane console = new GridPane();
	
	public static final int OFFSET = 20;
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
	private Image turtlePic;
	private int count = 0;
	
	public IDEWindow() {
		borderLayout = new BorderPane();
		primaryScene = new Scene(borderLayout, totalWidth, totalHeight, STANDARD_AREA_COLOR);
		turtleField = new Rectangle(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT, STANDARD_AREA_COLOR);
//		turtleGrid = new GridPane();
//		turtleGrid.setPrefSize(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT);
		leftBox = new VBox();
//		left.setPrefSize(LEFT_WIDTH, LEFT_HEIGHT);
		rightBox = new VBox();
//		right.setPrefSize(RIGHT_WIDTH, RIGHT_HEIGHT);
		topBox = new HBox();
		bottomBox = new HBox();
		
		console.setAlignment(Pos.CENTER);
		console.setHgap(10);
		//console.setVgap(2);
		console.setPadding(new Insets(25, 25, 25, 25));
		Text Console = new Text("Command History: ");
		console.add(Console, 0, count);
		rightGroup.getChildren().add(console);
		
		//bottomBox.setPrefSize(BOTTOM_WIDTH, BOTTOM_HEIGHT);
		
		makeButtons(primaryStage);
		//makeButtons();
		
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
		TurtleView field = new TurtleView();
		field.displayInitialTurtle();
	}
	
	private void makeButtons(Stage s) {
		
		buttonMaker.makeGUIItem(e->openFile(s), topGroup, "Set Turtle Image");
		buttonMaker.makeGUIItem(e->help(), bottomGroup, "Help");
		commandTextField = textFieldMaker.makeReturnableTextField(e->storeCommand(), leftGroup, "Command");
		topBox.getChildren().add(topGroup);
		bottomBox.getChildren().add(bottomGroup);
		leftBox.getChildren().add(leftGroup);
		rightBox.getChildren().add(rightGroup);
		bGColorTextField = textFieldMaker.makeReturnableTextField(e->changeBGColor(), topGroup, "BackGround Color");
		penColorTextField = textFieldMaker.makeReturnableTextField(e->changePenColor(), topGroup, "Pen Color");
		//bottomBox.getChildren().add(bottomGroup);
	}
	
	private void changeBGColor() {
		bGColorTextField.getText();
	}
	
	private void changePenColor() {
		penColorTextField.getText();
	}
	
	private void storeCommand()
	{
		Text history = new Text(commandTextField.getText()+"\n");
		count++;
		console.add(history, 0, count);
	}
	
	private void openFile(Stage s) {
		File dataFile = null; 
		dataFile = myChooser.showOpenDialog(s);
		if (dataFile != null) {
			String fileLocation = dataFile.toURI().toString();
			Image turtlePic = new Image(fileLocation);  
		}
	}
	/**
	 * @param extensionAccepted
	 * @return This method makes the FileChooser object that allows users to open an
	 *         XML File.
	 */
	private FileChooser makeChooser(String extensionAccepted) {
		FileChooser result = new FileChooser();
		//result.setTitle(myResources.getString("open"));
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
		//TextFlow textFlow = new TextFlow();
		//textFlow.getChildren().add(t);
		//Group group = new Group(textFlow);
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
