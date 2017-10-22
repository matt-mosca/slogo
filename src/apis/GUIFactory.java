package apis;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;

/**
 * Creates GUI items to be used in display class. Extending 
 * the interface will be used to create specific items
 * such as buttons, text fields, menu items, and etc
 */

public interface GUIFactory{
	
	/**
	 * This method will create a GUI item that will assign it 
	 * an action event (handle) and a label (name). This item will
	 * be stored in a Group (root).
	 * @param handle - This is the action event that the GUI item will enact when interacting
	 * @param root - This is the group that the GUI item will be stored in
	 * @param name - This is the string that will be the GUI item’s label
	 */
	void makeGUIItem (EventHandler<ActionEvent> handle, Group root, String name);
	
}