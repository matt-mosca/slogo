package frontend.window_setup;

import backend.Controller;
import backend.error_handling.SLogoException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author Ben Schwennesen
 */
public class Console {

    private final int PREFERED_WIDTH = 800;
    private final int PREFERED_HEIGHT = 200;
    private ObservableList<Node> commandElements;
    private ListView<Node> commandsListView;
    private Controller controller;
    private TextArea commandEntry;

    private int index;

    public Console(Controller controllerSample) {
    	controller = controllerSample;
    	commandEntry = new TextArea();
    	//commandEntry.setMaxHeight(100);
        commandElements = FXCollections.observableArrayList();
        commandElements.add(commandEntry);
        commandsListView = new ListView<>(commandElements);
        commandEntry.setOnKeyPressed(e -> handleKeyPress(e));
        commandsListView.setMaxWidth(PREFERED_WIDTH);
        commandsListView.setMaxHeight(PREFERED_HEIGHT);
    }

    private void handleKeyPress(KeyEvent keyPress) {
        if (keyPress.isShiftDown() && keyPress.getCode().equals(KeyCode.ENTER)) {
            // send to Controller here
        	String commandInput = commandEntry.getText();
        	checkCommand(commandInput);
            index = commandElements.size() - 1;
        } else if (keyPress.isShiftDown() && keyPress.getCode().equals(KeyCode.UP)
                && index > 0 && index <= commandElements.size()
                && commandElements.get(index - 1) instanceof Text) {
            index--;
            commandEntry.clear();
            commandEntry.insertText(0, ((Text) commandElements.get(index)).getText());
        } else if (keyPress.isShiftDown() && keyPress.getCode().equals(KeyCode.DOWN)) {
            if (index >= -1 && index <= getIndexOfLastEnteredCommand()
                    && commandElements.get(index + 1) instanceof Text) {
                index++;
                commandEntry.clear();
                commandEntry.insertText(0, ((Text) commandElements.get(index)).getText());
            } else if (index == getIndexOfLastEnteredCommand()) {
                commandEntry.clear();
            }

        }
    }
	private void checkCommand(String commandInput) {
		try {
			if(controller.validateCommand(commandInput)){
				controller.executeCommand(commandInput);
				addCommand(commandInput);
			}
		}
		// make custom method for errors if Controller throws error
		catch(SLogoException e) {
			addError(e.getMessage());
		}
		commandEntry.clear();
		commandsListView.scrollTo(getIndexOfLastEnteredCommand());
	}

    void addCommand(String command) {
        Text commandElement = new Text(command);
        // change event below to send to Controller
        commandElement.setOnMouseClicked(e -> checkCommand(command));
        commandElements.add(getIndexOfLastEnteredCommand() + 1, commandElement);
    }
    void addError(String error) {
    	Text commandError = new Text(error);
        // change event below to send to Controller
    	commandError.setFill(Color.RED);
        commandElements.add(getIndexOfLastEnteredCommand() + 1, commandError);
    }

    public ListView<Node> getListView() { return commandsListView; }
    
    public void setCommandEntry(String function) {
    	commandEntry.setText(function);
    }
    

    private int getIndexOfLastEnteredCommand() { return commandElements.size() - 2; }
}
