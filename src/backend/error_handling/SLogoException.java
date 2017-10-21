package backend.error_handling;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utilities.ExceptionMessageGetter;

/**
 * Not thrown; message is displayed in frontend.
 *
 * @author Ben Schwennesen
 */
public abstract class SLogoException {

    private StringProperty message = new SimpleStringProperty();

    public SLogoException() {
        registerExceptionListener();
    }

    void setMessageUsingInstanceName() {
        setMessage(ExceptionMessageGetter.getMessage(this.getClass().getName()));
    }

    protected void setMessage(String message) {
        this.message.setValue(message);
    }

    // TODO - change / uncomment when frontend is ready
    private void registerExceptionListener() {
        message.addListener(((observable, oldValue, newValue) -> {
            //commandConsole.print(newValue);
            System.out.println(newValue);
        }));
    }
}
