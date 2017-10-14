package apis;


/**
 * Stores the last generated error message. There will be an event listener on setMessage().
 */
public interface ErrorMessage {

    /**
     * Set the current error message when bad commands are provided.
     *
     * @param message - an error message generated on account of a syntax error
     */
    void setMessage(String message); // package private

    /**
     * Get the current error message.
     *
     * @return the last generated error message (empty string if none was ever generated)
     */
	String getMessage();
}

