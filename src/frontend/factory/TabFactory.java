package frontend.factory;

import javafx.scene.Group;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabFactory {
	public void makeTab(String name, Group root, TabPane tabPane) {
		Tab tab = new Tab(name);
		tab.setContent(root);
		tabPane.getTabs().add(tab);
	}
}
