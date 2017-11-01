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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ben Schwennesen
 */
public class Console {

    private final int PREFERED_WIDTH = 1050;
    private final int PREFERED_HEIGHT = 200;

    private ObservableList<Node> consoleElements;
    private ListView<Node> commandsListView;
    private List<String> commandHistory;
    private Controller controller;
    private TextArea commandEntry;
    private int elementIndex;

    public Console(Controller controllerSample) {
    	controller = controllerSample;
    	commandEntry = new TextArea();
        consoleElements = FXCollections.observableArrayList();
        consoleElements.add(commandEntry);
        commandsListView = new ListView<>(consoleElements);
        commandEntry.setOnKeyPressed(e -> handleKeyPress(e));
        commandsListView.setMaxWidth(PREFERED_WIDTH);
        commandsListView.setMaxHeight(PREFERED_HEIGHT);
        commandHistory = new ArrayList<>();
    }

    private void handleKeyPress(KeyEvent keyPress) {
        if (keyPress.isShiftDown() && keyPress.getCode().equals(KeyCode.ENTER)) {
            processEnteredCommand();
        }
        else if (keyPress.isShiftDown() && keyPress.getCode().equals(KeyCode.UP)
                && elementIndex > 0 && elementIndex <= consoleElements.size()
                && consoleElements.get(elementIndex - 1) instanceof Text) {
            moveUpCommandHistory();
        } else if (keyPress.isShiftDown() && keyPress.getCode().equals(KeyCode.DOWN)) {
            if (elementIndex >= -1 && elementIndex <= getIndexOfLastEnteredCommand()
                    && consoleElements.get(elementIndex + 1) instanceof Text) {
                moveDownCommandHistory();
            } else if (elementIndex == getIndexOfLastEnteredCommand()) {
                commandEntry.clear();
            }
        }
    }

    private void processEnteredCommand() {
        String commandInput = commandEntry.getText();
        if (commandInput.trim().length() > 0) {
            checkCommand(commandInput);
            elementIndex = consoleElements.size() - 1;
        }
    }

    private void moveUpCommandHistory() {
        elementIndex--;
        commandEntry.clear();
        commandEntry.insertText(0, new Text(commandHistory.get(elementIndex)).getText());
    }

    private void moveDownCommandHistory() {
        elementIndex++;
        commandEntry.clear();
        commandEntry.insertText(0, new Text(commandHistory.get(elementIndex)).getText());
    }

    private void checkCommand(String commandInput) {
		try {
			if(controller.validateCommand(commandInput)){
				controller.executeCommand(commandInput);
				addCommand(commandInput);
			}
		}
		catch(SLogoException e) {
			addError(e.getMessage());
		}
		commandEntry.clear();
        commandHistory.add(commandInput);
		commandsListView.scrollTo(getIndexOfLastEnteredCommand());
	}


    private void addCommand(String command) {
        Text commandElement = new Text(command);
        // change event below to send to Controller
        commandElement.setOnMouseClicked(e -> checkCommand(command));
        consoleElements.add(getIndexOfLastEnteredCommand() + 1, commandElement);
    }

    void addError(String error) {
    	Text commandError = new Text(error);
        // change event below to send to Controller
    	commandError.setFill(Color.RED);
        consoleElements.add(getIndexOfLastEnteredCommand() + 1, commandError);
    }

    private int getIndexOfLastEnteredCommand() { return consoleElements.size() - 2; }
    
    void setCommandEntry(String function) { commandEntry.setText(function); }

    ListView<Node> getListView() { return commandsListView; }
}
