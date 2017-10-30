package main;

import frontend.factory.ButtonFactory;
import frontend.factory.TextFieldFactory;
import frontend.window_setup.HelpWindow;
import frontend.window_setup.IDEWindow;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application {
	public static final String APP_NAME = "SLogo";
	private static final Paint STANDARD_AREA_COLOR = Color.AQUA;
	public static final double TOTAL_WIDTH = 400;
	public static final double TOTAL_HEIGHT = 400;
	private ButtonFactory buttonMaker = new ButtonFactory();
	private TextFieldFactory textFieldMaker = new TextFieldFactory();
	private Stage primaryStage;
	private Group root = new Group();
	private HBox hbox = new HBox();
	private String stageName = "SLogo";
	private TextField textField;
	private Scene primaryScene;
	private HelpWindow helpWindow = new HelpWindow();
	
	public void start(Stage primary) {
		primaryStage = primary;
		primaryStage.setTitle(APP_NAME);
		primaryStage.setResizable(false);
		primaryStage.show();
		buttonMaker.makeGUIItem(e->createIDEWindow(), root, "Create SLogo Workspace");
		textField = textFieldMaker.makeReturnableTextField(e->setStageName(), root, "Name SLogo Workspace");
		buttonMaker.makeGUIItem(e->helpWindow.help(), root, "Help");
		hbox.getChildren().addAll(root.getChildren());
		primaryScene = new Scene(hbox, TOTAL_WIDTH, TOTAL_HEIGHT, STANDARD_AREA_COLOR);
		primaryStage.setScene(primaryScene);
	}
	private void setStageName() {
		stageName = textField.getText();
	}

	private void createIDEWindow() {
		Stage newStage = new Stage();
		newStage.setTitle(stageName);
		newStage.setResizable(false);
		newStage.show();
		IDEWindow window = new IDEWindow(newStage);
		window.setUpWindow();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
