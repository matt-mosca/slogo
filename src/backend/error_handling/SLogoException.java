package backend.error_handling;

import utilities.ExceptionMessageGetter;

/**
 * Message is displayed in frontend.
 *
 * @author Ben Schwennesen
 */
public abstract class SLogoException extends Throwable {

    public String getMessage() {
        return ExceptionMessageGetter.getMessage(this.getClass().getName());
    }

}
