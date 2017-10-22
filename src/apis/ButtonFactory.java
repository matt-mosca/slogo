package apis;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class ButtonFactory implements GUIFactory{
	
	public void makeGUIItem (EventHandler<ActionEvent> handle, Group root, String name)
	{
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handle);
		root.getChildren().add(sampleButton);
	}
}
