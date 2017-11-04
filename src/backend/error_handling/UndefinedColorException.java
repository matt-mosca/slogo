package backend.error_handling;

/**
 * Exception thrown when the user attempts to access an undefined color index.
 *
 * @author Ben Schwennesen
 */
public class UndefinedColorException extends SyntaxCausedException {

    /**
     * Construct an exception to throw when the user tries to access an undefined color.
     *
     * @param colorIndex - the undefined color index
     */
    public UndefinedColorException(int colorIndex) {
        super(String.valueOf(colorIndex));
    }
}
