package frontend.factory;

import javafx.scene.control.Menu;

public class MenuFactory {
	MenuItemFactory menuItemMaker = new MenuItemFactory();
	public Menu makeMenu(String name,String[] menuItemList) {
		Menu menu = new Menu(name);
		for(int i = 0; i<menuItemList.length;i++) {
			//menuItemMaker.makeMenuItem(setLangue, "Chinese");
		}
		return menu;
	}
}
