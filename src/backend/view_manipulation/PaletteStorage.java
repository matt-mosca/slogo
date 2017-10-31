package backend.view_manipulation;

import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedColorException;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;
import java.util.TreeMap;

/**
 * @author Ben Schwennesen
 */
public class PaletteStorage extends Observable {

    private final String PALETTE_DEFAULTS_FILE = "resources/DefaultPalette.properties";
    private final Properties DEFAULT_PALETTE_PROPERTIES;

    private Map<Integer, Color> colorMap = new TreeMap<>();

    private final double MAX_COLOR_AMOUNT = 256;

    public PaletteStorage() {
        DEFAULT_PALETTE_PROPERTIES = new Properties();
        try {
            InputStream paletteStream = getClass().getClassLoader().getResourceAsStream(PALETTE_DEFAULTS_FILE);
            DEFAULT_PALETTE_PROPERTIES.load(paletteStream);
        } catch (IOException e) {
            // won't happen
        }
        setDefaultColors();
    }

    private void setDefaultColors() {
        for (String key : DEFAULT_PALETTE_PROPERTIES.stringPropertyNames()) {
            colorMap.put(Integer.parseInt(key), Color.valueOf(DEFAULT_PALETTE_PROPERTIES.getProperty(key)));
        }
    }

    double setColorAtIndex(int index, int red, int green, int blue) {
        colorMap.put(index, Color.color(red/MAX_COLOR_AMOUNT, green/MAX_COLOR_AMOUNT, blue/MAX_COLOR_AMOUNT));
        return index;
    }

    Color getColor(int index) throws SLogoException {
        if (!colorMap.containsKey(index)) {
            throw new UndefinedColorException(index);
        }
        return colorMap.get(index);
    }


    public Map<Integer, Color> getAvailableColors() { return colorMap; }
}
