package frontend.factory;

import apis.GUIFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TextField;

public class TextFieldFactory implements GUIFactory {

	@Override
	public void makeGUIItem(EventHandler<ActionEvent> handle, Group root, String name)  {
		TextField sampleText = makeReturnableTextField(handle,root,name);	
	}
	public TextField makeReturnableTextField(EventHandler<ActionEvent> handle, Group root, String name) {
		TextField returnText = new TextField();
		returnText.setPromptText(name);
		returnText.setOnAction(handle);
		root.getChildren().add(returnText);
		return returnText;
	}
}
