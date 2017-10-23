package backend.error_handling;

import utilities.ExceptionMessageGetter;

public class SyntaxCausedException extends SLogoException {

    private final String EXCEPTION_CAUSE;

    public SyntaxCausedException(String exceptionCauseString) {
        EXCEPTION_CAUSE = exceptionCauseString;
    }

    @Override
    public String getMessage() {
        String className = this.getClass().getName();
        return String.format(ExceptionMessageGetter.getMessage(className), EXCEPTION_CAUSE);
    }
}
