package frontend.window_setup;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class HelpWindow {
	public static final int TEXT_SIZE = 12;
	public static final int WRAPPING_WIDTH = 450;
	public static final int SCENE_WIDTH = 500;
	public static final int SCENE_HEIGHT = 200;
	
	private Stage helpStage;
	public HelpWindow() {
		helpStage = new Stage();
	}
	public void help() {
		Text t = assembleDisplayedText();
		t.setFont(new Font(TEXT_SIZE));
		t.setWrappingWidth(WRAPPING_WIDTH);
		t.setTextAlignment(TextAlignment.JUSTIFY);

		VBox vbox = new VBox();
		vbox.getChildren().add(t);
		Scene scene = new Scene(vbox, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);
		helpStage.setTitle("Help");
		helpStage.setScene(scene);
		helpStage.show();
	}
	private Text assembleDisplayedText() {
		Text textToDisplay = new Text();
		String text = new String();
		StringBuilder textBuilder = new StringBuilder();
		textBuilder.append("Help : \n");
		textBuilder.append("The user can control the turtle and pen in several ways. \n");
		textBuilder.append("1. The user can enter commands in Logo. The commands will be displayed in the"
				+ " \'Command History\' area on the right side of the window. \n");
		textBuilder.append("2. The user can use buttons provided in the display to control the turtle and pen. "
				+ "The user can click these buttons or move between them with the arrow keys and press "
				+ "them with \'Space.\' \n");
		textBuilder.append("3. When a text box is not selected, the user can use WASD to control the "
				+ "movement of the turtle, and \'Space\' to pick the pen up or put it down. \n \n");
		textBuilder.append("Commands: \n");
		text = new String(textBuilder);
		textToDisplay.setText(text);
		return textToDisplay;
	}
}
