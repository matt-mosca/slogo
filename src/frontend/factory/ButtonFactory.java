package frontend.factory;

import apis.GUIFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ButtonFactory implements GUIFactory {
	public static final int IMAGE_SIZE = 10;
	
	public void makeGUIItem (EventHandler<ActionEvent> handle, Group root, String name) {
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handle);
		root.getChildren().add(sampleButton);
	}
	
	public void makeTextGUIItemInGrid (EventHandler<ActionEvent> handle, GridPane grid, String name, int column, int row) {
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handle);
		grid.add(sampleButton, column, row);
	}
	public void makeImageGUIItemInGrid (EventHandler<ActionEvent> handle, GridPane grid, ImageView pic, int column, int row) {
		pic.setFitHeight(IMAGE_SIZE);
		pic.setFitWidth(IMAGE_SIZE);
		Button sampleButton = new Button("", pic);
		sampleButton.setOnAction(handle);
		grid.add(sampleButton, column, row);
	}
}
