package frontend.factory;

import apis.GUIFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ButtonFactory implements GUIFactory {
	
	public void makeGUIItem (EventHandler<ActionEvent> handle, Group root, String name)
	{
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handle);
		root.getChildren().add(sampleButton);
	}
	public void makeGUIItemInGrid (EventHandler<ActionEvent> handle, GridPane grid, String name, int column, int row)
	{
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handle);
		grid.add(sampleButton, column, row);
	}
}
