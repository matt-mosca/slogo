package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.scene.paint.Color;

/**
 * Static utility class for retrieving various properties. Extends the functionality of the base java class
 * java.util.Properties. All methods are static and fields are intialized in a static block.
 *
 * @author Ben Schwennesen
 */
public final class PropertiesGetter {

    private static final String PROPERTIES_FILE = ".properties";
    private static final Properties PROPERTIES;

    /**
     * Blank, private constructor to ensure no other class tries to create an instance of this
     * utility class.
     */
    private PropertiesGetter() {
        // do nothing
    }

    /** static block to initialize the static java.util.Properties member */
    static {
        PROPERTIES = new Properties();
        InputStream properties = PropertiesGetter.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE);
        try {
            PROPERTIES.load(properties);
        } catch (IOException failure) {
            /* do nothing: if file fails to load, all methods are prepared to return
             * default/fallback value when getProperty() returns null */
        }
    }

    /**
     * Get a property that should stay as a string.
     *
     * @param propertyName - the name of the property in question
     * @return the corresponding entry in the properties file
     */
    public static String getProperty(String propertyName) {
        return PROPERTIES.containsKey(propertyName) ? PROPERTIES.getProperty(propertyName) : "";
    }

    /**
     * Get a property that is know to be an integer.
     *
     * @param key - the key used to index the desired configuration value
     * @return value - the integer configuration value we want to get
     */
    private static int getIntegerProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        // if the key is not found, Properties will return null and we should return a default value
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    /**
     * Get a property that is know to be a double.
     *
     * @param key - the key used to index the desired configuration value
     * @return value - the double configuration value we want to get
     */
    private static Double getDoubleProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        // if the key is not found, Properties will return null and we should return a default value
        if (value == null) {
            return 0.0;
        }
        return Double.parseDouble(value);
    }

}
