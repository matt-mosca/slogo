package apis;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class Display extends Application {
	
	private Stage myStage;
	private Stage helpStage;
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private Timeline animation = new Timeline();
	private HBox hboxBottom = new HBox();
	private HBox hboxTop = new HBox();
	private Group hboxBottomGroup = new Group();
	private Group hboxTopGroup = new Group();
	public static final int OFFSET = 20;
	private static final Color BACKGROUND = Color.BLACK;
	private static final String TITLE = "SLogo";
	private Group splash = new Group();
	private Group helpText = new Group();
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final String DATA_FILE_EXTENSION = "*.jpg";
	private BorderPane screenBorder = new BorderPane();
	private static final int VERT_SIZE = 650;
	private static final int HORIZONTAL_SIZE = 575;
	ButtonFactory buttonMaker = new ButtonFactory();
	TextFieldFactory textFieldMaker = new TextFieldFactory();
	private Image turtlePic;
	
	/**	
	 * This method starts the application by adding the initial buttons to the
	 * original screen
	 */
	
	public void start(Stage primaryStage) throws Exception {
		setUpGUI(primaryStage);
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

		/**
		 * @param s
		 * @throws Exception
		 *             This method adds buttons to the Border Pane and calls the
		 *             startSplash and addEvents methods, creating the basic look of the
		 *             screen when no movement has been called yet.
		 */
		private void setUpGUI(Stage s) throws Exception {
			makeButtons(s);
			hboxTop.setPadding(new Insets(OFFSET));
			hboxTop.setSpacing(OFFSET);

			screenBorder.setTop(hboxTop);
			screenBorder.setBottom(hboxBottom);

			screenBorder.setPrefSize(HORIZONTAL_SIZE, VERT_SIZE);
			screenBorder.getStyleClass().add("pane");
			splash.getChildren().add(screenBorder);

			Scene scene = new Scene(splash, HORIZONTAL_SIZE, VERT_SIZE);
			setUpStage(s, scene);
		}
		
		private void setUpStage(Stage s, Scene scene) {
			//myStage = s;
			//myStage.setScene(scene);
			//myStage.setTitle(TITLE);
			//myStage.show();
			//myStage = s;
			s.setScene(scene);
			s.setTitle(TITLE);
			s.show();
		}

		/**
		 * @param s
		 *            Makes the generic buttons every simulation needs, calls
		 *            makeSimSpecificFields to make the buttons specific to each
		 *            subsimulation.
		 */
		private void makeButtons(Stage s) {
			
			buttonMaker.makeGUIItem(e->openFile(s), hboxTopGroup, "Set Turtle Image");
			buttonMaker.makeGUIItem(e->help(), hboxBottomGroup, "Help");
			hboxTop.getChildren().add(hboxTopGroup);
			hboxBottom.getChildren().add(hboxBottomGroup);
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
			//t.setFont(new Font(20));
			//t.setWrappingWidth(200);
			//t.setTextAlignment(TextAlignment.JUSTIFY);
			t.setText("The quick brown fox jumps over the lazy dog");
			TextFlow textFlow = new TextFlow();
			textFlow.getChildren().add(t);
			Group group = new Group(textFlow);
			Scene scene = new Scene(group, 500, 150, Color.WHITE);
			helpStage.setTitle("Hello Rich Text");
			helpStage.setScene(scene);
			helpStage.show();
		}
		/**
		 * @param args
		 *            Main launch method
		 */
		public static void main(String[] args) {
			launch(args);
		}
	}