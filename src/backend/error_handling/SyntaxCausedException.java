package backend.error_handling;

import utilities.ExceptionMessageGetter;

/**
 * Abstract exception representing errors caused by bad user-entered syntax.
 *
 * @author Ben Schwennesen
 */
public abstract class SyntaxCausedException extends SLogoException {

    private final String EXCEPTION_CAUSE;

    /**
     * Construct a syntax caused exception.
     *
     * @param exceptionCauseToken - the user-entered token causing the error
     */
    public SyntaxCausedException(String exceptionCauseToken) {
        EXCEPTION_CAUSE = exceptionCauseToken;
    }

    @Override
    public String getMessage() {
        String className = this.getClass().getName();
        return String.format(ExceptionMessageGetter.getMessage(className), EXCEPTION_CAUSE);
    }
}
