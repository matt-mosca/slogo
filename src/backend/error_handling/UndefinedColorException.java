package backend.error_handling;

/**
 * @author Ben Schwennesen
 */
public class UndefinedColorException extends SyntaxCausedException {
    public UndefinedColorException(double colorIndex) {
        super(String.valueOf(colorIndex));
    }
}
