package backend.error_handling;

/**
 * Exception to throw in the case that the user tries to access an image that is not defined.
 *
 * @author Ben Schwennesen
 */
public class UndefinedImageException extends SyntaxCausedException {

    /**
     * Construct an undefined image exception to throw.
     *
     * @param imageIndex - the undefined image index
     */
    public UndefinedImageException(double imageIndex) {
        super(String.valueOf(imageIndex));
    }
}
