package test;

import frontend.window_setup.Console;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Ben Schwennesen
 */
public class ConsoleTester extends Application{

        public static final String APP_NAME = "Test";

        private Stage primaryStage;

        public void start(Stage primary) {
            primaryStage = primary;
            primaryStage.setTitle(APP_NAME);
            primaryStage.setResizable(false);
            primaryStage.show();
            Console window = new Console(null);
            // primaryStage.setScene(new Scene(window.getListView()));
            // ^ made getListView package private
        }

        public static void main(String[] args) {
            launch(args);
        }
}
