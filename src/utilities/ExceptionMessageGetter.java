package utilities;

import backend.error_handling.ProjectBuildException;
import backend.error_handling.SLogoException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Static utility class for retrieving exception messages. Extends the functionality of the base java class
 * java.util.Properties. All methods are static and fields are intialized in a static block.
 *
 * @author Ben Schwennesen
 */
public final class ExceptionMessageGetter {

    private static final String PROPERTIES_FILE = "resources/Exceptions.properties";
    private static final Properties PROPERTIES;

    /**
     * Blank, private constructor to ensure no other class tries to create an instance of this
     * utility class.
     */
    private ExceptionMessageGetter() {
        // do nothing
    }

    /** static block to initialize the static java.util.Properties member */
    static {
        PROPERTIES = new Properties();
        InputStream properties = ExceptionMessageGetter.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE);
        try {
            PROPERTIES.load(properties);
        } catch (IOException failure) {
            SLogoException exception = new ProjectBuildException();

        }
    }

    /**
     * Get a property that should stay as a string.
     *
     * @param propertyName - the name of the property in question
     * @return the corresponding entry in the properties file
     */
    public static String getMessage(String propertyName) {
        return PROPERTIES.containsKey(propertyName) ? PROPERTIES.getProperty(propertyName) : "";
    }

}
