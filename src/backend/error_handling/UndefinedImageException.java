package backend.error_handling;

/**
 * @author Ben Schwennesen
 */
public class UndefinedImageException extends SyntaxCausedException {
    public UndefinedImageException(double imageIndex) {
        super(String.valueOf(imageIndex));
    }
}
