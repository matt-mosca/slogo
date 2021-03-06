package frontend.factory;

import apis.GUIFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.TextArea;

public class TextAreaFactory implements GUIFactory {

	@Override
	public void makeGUIItem(EventHandler<ActionEvent> handle, Group root, String name)  {
		TextArea sampleText = makeReturnableTextArea(handle,root,name);	
	}
	public TextArea makeReturnableTextArea(EventHandler<ActionEvent> handle, Group root, String name) {
		TextArea returnText = new TextArea();
		root.getChildren().add(returnText);
		return returnText;
	}
}