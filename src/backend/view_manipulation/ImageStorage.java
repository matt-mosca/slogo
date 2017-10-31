package backend.view_manipulation;

import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedColorException;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ben Schwennesen
 */
public class ImageStorage {

    private final String IMAGES_PATH = "images/";
    private final String IMAGE_EXTENSION = ".png";

    private final Map<Integer, Image> IMAGE_MAP = new TreeMap<>();

    public ImageStorage() {
        fillImageMap();
    }

    private void fillImageMap() {
        File imageDirectory = new File(IMAGES_PATH);
        String[] filesNames = imageDirectory.list();
        // for logical ordering
        Arrays.sort(filesNames);
        for (String s : filesNames) System.out.print(s);
        int index = 1;
        for (String fileName : filesNames) {
            if (fileName.endsWith(IMAGE_EXTENSION)) {
                IMAGE_MAP.put(index++, new Image(getClass().getClassLoader().getResourceAsStream(fileName)));
            }
        }
    }

    Map<Integer, Image> getImageMap() { return IMAGE_MAP; }

    Image getImage(int index) throws SLogoException {
        if (!IMAGE_MAP.containsKey(index)) {
            throw new UndefinedColorException(index);
        } else {
            return IMAGE_MAP.get(index);
        }
    }
}
