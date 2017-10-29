package frontend.factory;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuBarFactory {
	MenuItemFactory menuItemMaker = new MenuItemFactory();
	MenuBar languageMenuBar = new MenuBar();
	Menu languageMenu = new Menu("Language");
	String[] languageList = {"Chinese","English","French", "German", "Italian", "Portuguese", "Russian", "Spanish"};
	
	public MenuBar makeMenuItem(EventHandler<ActionEvent> handle, String name)
	{
		for(int i = 0; i<languageList.length;i++)
		{
			menuItemMaker.makeMenuItem(e->setMenuLanguage("Chinese"), "Chinese");
		}
}
