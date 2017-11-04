package backend.error_handling;

import utilities.ExceptionMessageGetter;

/**
 * Abstraction of the exception thrown when a SLogo error occurs. The names of subclasses are used to obtain the error
 * message associated with the particular exceptions from a properties file.
 *
 * @author Ben Schwennesen
 */
public abstract class SLogoException extends Throwable {

    /**
     * Get the message associated with a particular SLogo exception.
     *
     * @return the appropriate message for the exception type, obtained through reflection on the exception name
     */
    public String getMessage() {
        return ExceptionMessageGetter.getMessage(this.getClass().getName());
    }

}
