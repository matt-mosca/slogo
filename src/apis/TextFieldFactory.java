package apis;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TextField;

public class TextFieldFactory implements GUIFactory {

	@Override
	public void makeGUIItem(EventHandler<ActionEvent> handle, Group root, String name) 
	{
		TextField sampleText = new TextField();
		sampleText.setPromptText(name);
		sampleText.setOnAction(handle);
		root.getChildren().add(sampleText);	
	}
}
