package backend.error_handling;

import utilities.ExceptionMessageGetter;

/**
 *
 *
 * Name of this class is used in super class to determine the message of the error.
 *
 * @author Ben Schwennesen
 */
public class UndefinedCommandException extends SLogoException {

    private final String UNDEFINED_COMMAND_TOKEN;

    public UndefinedCommandException(String commandToken) {
        UNDEFINED_COMMAND_TOKEN = commandToken;
    }

    @Override
    public void registerMessage() {
        setMessage(ExceptionMessageGetter.getMessage(this.getClass().getName()) + UNDEFINED_COMMAND_TOKEN);
    }

}
