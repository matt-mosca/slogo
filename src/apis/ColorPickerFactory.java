package apis;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;

public class ColorPickerFactory implements GUIFactory{
	
	public void makeGUIItem (EventHandler<ActionEvent> handle, Group root, String name)
	{
		ColorPicker sampleColorPicker= new ColorPicker();
		sampleColorPicker.setPromptText(name);
		sampleColorPicker.setOnAction(handle);
		root.getChildren().add(sampleColorPicker);
	}
}
