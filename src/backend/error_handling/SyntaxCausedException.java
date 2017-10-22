package backend.error_handling;

import utilities.ExceptionMessageGetter;

public class SyntaxCausedException extends SLogoException {

    private final String EXCEPTION_CAUSE;

    public SyntaxCausedException(String exceptionCauseString) {
        EXCEPTION_CAUSE = exceptionCauseString;
    }

    @Override
    public void registerMessage() {
        String className = this.getClass().getName();
        setMessage(String.format(ExceptionMessageGetter.getMessage(className), EXCEPTION_CAUSE));
    }
}
