package utilities;

import backend.error_handling.ProjectBuildException;
import backend.error_handling.SLogoException;
import frontend.window_setup.IDEWindow;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * @author Ben Schwennesen
 */
public class MenuGetter {

    public static final String INFO_DELIMITER = ",";
    private final int INFO_LENGTH = 2;
    private final String MENU_INFO_FILE = "resources/MenuBar.properties";
    private final Class MENU_RUNNABLES_CLASS = IDEWindow.class;
    private final Properties MENU_PROPERTIES = new Properties();

    public MenuGetter() throws SLogoException {
        try {
            InputStream commandPropertiesStream = getClass().getClassLoader().getResourceAsStream(MENU_INFO_FILE);
            MENU_PROPERTIES.load(commandPropertiesStream);
        } catch (IOException fileNotFound) {
            throw new ProjectBuildException();
        }
    }

    public List<Menu> getMenuDropdowns(IDEWindow runner) throws SLogoException {
        Map<String, Menu> dropdownsMap = new TreeMap<>(Collections.reverseOrder());
        for (String itemName : MENU_PROPERTIES.stringPropertyNames()) {
            generateMenuItem(dropdownsMap, itemName, runner);
        }
        return new ArrayList<>(dropdownsMap.values());
    }

    private void generateMenuItem(Map<String, Menu> dropdownsMap, String itemName, IDEWindow runner) throws ProjectBuildException {
    	System.out.println(itemName);
        String[] dropdownInfo = MENU_PROPERTIES.getProperty(itemName).split(INFO_DELIMITER);
        if (dropdownInfo.length != INFO_LENGTH) {
            throw new ProjectBuildException();
        }
        String dropdownName = dropdownInfo[0];
        Menu dropdown = dropdownsMap.getOrDefault(dropdownName, new Menu(dropdownName));
        try {
            Method actionMethod = runner.getClass().getDeclaredMethod(dropdownInfo[1]);
            System.out.println(actionMethod.getName());
            MenuItem menuItem = new MenuItem(itemName);
            menuItem.setOnAction(e -> runMenuAction(actionMethod, runner));
            dropdown.getItems().add(menuItem);
        } catch (ReflectiveOperationException badMethod) {
            throw new ProjectBuildException();
        }
        dropdownsMap.put(dropdownName, dropdown);
    }

    private void runMenuAction(Method actionMethod, IDEWindow runner) {
        try {
            actionMethod.setAccessible(true);
            actionMethod.invoke(runner);
        } catch (ReflectiveOperationException callFailure) {
            return;
        }
    }
}
