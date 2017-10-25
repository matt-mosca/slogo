package utilities;

import backend.error_handling.ProjectBuildException;
import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedColorException;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

public class PaletteStorage extends Observable {

    private final Properties DEFAULT_PALETTE_PROPERTIES;
    private final String PALETTE_DEFAULTS_FILE = "resources/DefaultPalette.properties";

    private Map<Double, Color> colorMap = new HashMap<>();

    private double currentPenColorIndex;
    private double currentBackgroundColorIndex;

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
            colorMap.put(Double.parseDouble(key), Color.valueOf(DEFAULT_PALETTE_PROPERTIES.getProperty(key)));
        }
    }

    void setColorAtIndex(double index, int red, int green, int blue) {
        colorMap.put(index, Color.color(red/MAX_COLOR_AMOUNT, green/MAX_COLOR_AMOUNT, blue/MAX_COLOR_AMOUNT));
    }

    void setPenColor(double index) throws SLogoException {
        if (!colorMap.containsKey(index)) {
            throw new UndefinedColorException(index);
        }
        currentPenColorIndex = index;
    }

    void setBackgroundColor(double index) throws SLogoException {
        if (!colorMap.containsKey(index)) {
            throw new UndefinedColorException(index);
        }
        currentBackgroundColorIndex = index;
    }

    public Color getCurrentPenColor() { return colorMap.get(currentPenColorIndex); }

    double getCurrentPenColorIndex() { return currentPenColorIndex; }

    public Color getCurrentBackgroundColor() { return colorMap.get(currentBackgroundColorIndex); }

    double getCurrentBackgroundColorIndex() { return currentBackgroundColorIndex; }

    public Map<Double, Color> getAvailableColors() { return colorMap; }
}
