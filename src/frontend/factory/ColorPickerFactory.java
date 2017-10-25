package frontend.factory;

import apis.GUIFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ColorPickerFactory implements GUIFactory{
	
	public void makeGUIItem (EventHandler<ActionEvent> handle, Group root, String name)
	{
		ColorPicker sampleColorPicker = makeReturnableColorPicker(handle, root, name);
	}
	public ColorPicker makeReturnableColorPicker(EventHandler<ActionEvent> handle, Group root, String name) {
		ColorPicker sampleColorPicker= new ColorPicker();
		Text pickerLabel = new Text(name);
		VBox vbox = new VBox();
		sampleColorPicker.setOnAction(handle);
		vbox.getChildren().add(pickerLabel);
		vbox.getChildren().add(sampleColorPicker);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		root.getChildren().add(vbox);
		return sampleColorPicker;
	}
}
