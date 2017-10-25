package utilities;

import backend.error_handling.ProjectBuildException;
import backend.error_handling.SLogoException;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Palette {

    private final Properties DEFAULT_PALETTE_PROPERTIES;
    private final String PALETTE_DEFAULTS_FILE = "resources/DefaultPalette.properties";

    private Map<Integer, Color> colorMap = new HashMap<>();

    private Color currentColor; 

    public Palette() throws SLogoException {
        DEFAULT_PALETTE_PROPERTIES = new Properties();
        try {
            InputStream paletteStream = getClass().getClassLoader().getResourceAsStream(PALETTE_DEFAULTS_FILE);
            DEFAULT_PALETTE_PROPERTIES.load(paletteStream);
        } catch (IOException e) {
            throw new ProjectBuildException();
        }
        setDefaultColors();
    }

    private void setDefaultColors() {
        for (String key : DEFAULT_PALETTE_PROPERTIES.stringPropertyNames()) {
            colorMap.put(Integer.parseInt(key), Color.valueOf(DEFAULT_PALETTE_PROPERTIES.getProperty(key)));
        }
    }
}
