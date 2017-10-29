package frontend.factory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

public class MenuFactory {
	MenuItemFactory menuItemMaker = new MenuItemFactory();
	public Menu makeMenu(String name,String[] menuItemList)
	{
		Menu menu = new Menu(name);
		for(int i = 0; i<menuItemList.length;i++)
		{
			//menuItemMaker.makeMenuItem(setLangue, "Chinese");
		}
		return menu;
	}
}
