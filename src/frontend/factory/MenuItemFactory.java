package frontend.factory;

import apis.GUIFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.MenuItem;


public class MenuItemFactory implements GUIFactory {

	@Override
	public void makeGUIItem(EventHandler<ActionEvent> handle, Group root, String name) 
	{
		MenuItem sampleMenuItem= new MenuItem(name);
		sampleMenuItem.setOnAction(handle);
		//(Menu) root = new Menu();
		//.getItems().add(sampleMenuItem);
	}

}
