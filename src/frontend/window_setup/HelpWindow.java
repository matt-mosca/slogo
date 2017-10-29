package frontend.window_setup;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class HelpWindow {
	public static final int TEXT_SIZE = 20;
	public static final int WRAPPING_WIDTH = 200;
	public static final int SCENE_WIDTH = 500;
	public static final int SCENE_HEIGHT = 150;
	
	private Stage helpStage;
	public HelpWindow() {
		helpStage = new Stage();
	}
	public void help() {
		Text t = assembleDisplayedText();
		t.setFont(new Font(TEXT_SIZE));
		t.setWrappingWidth(WRAPPING_WIDTH);
		t.setTextAlignment(TextAlignment.JUSTIFY);
		t.setText("Commands/help");

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
		textBuilder.append("Commands/help");
		text = new String(textBuilder);
		textToDisplay.setText(text);
		return textToDisplay;
	}
}
