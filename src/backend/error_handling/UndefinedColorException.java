package backend.error_handling;

/**
 * @author Ben Schwennesen
 */
public class UndefinedColorException extends SyntaxCausedException {
    public UndefinedColorException(int colorIndex) {
        super(String.valueOf(colorIndex));
    }
}
