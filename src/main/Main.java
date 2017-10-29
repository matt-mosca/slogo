package main;

import frontend.window_setup.IDEWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static final String APP_NAME = "SLogo";
	
	private Stage primaryStage;
	
	public void start(Stage primary) {
		primaryStage = primary;
		primaryStage.setTitle(APP_NAME);
		primaryStage.setResizable(false);
		primaryStage.show();
		IDEWindow window = new IDEWindow();
		window.setUpWindow(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
