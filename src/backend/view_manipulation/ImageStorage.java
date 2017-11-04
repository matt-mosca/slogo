package backend.view_manipulation;

import backend.error_handling.SLogoException;
import backend.error_handling.UndefinedColorException;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * Stores the turtle images defined at indices.
 *
 * @author Ben Schwennesen
 */
class ImageStorage {

    private final String IMAGES_PATH = "images/";
    private final String IMAGE_EXTENSION = ".png";

    private final Map<Integer, Image> IMAGE_MAP = new TreeMap<>();

    /**
     * Construct the turtle image storage.
     */
    ImageStorage() {
        fillImageMap();
    }

    private void fillImageMap() {
        File imageDirectory = new File(IMAGES_PATH);
        String[] filesNames = imageDirectory.list();
        // for logical ordering
        Arrays.sort(filesNames);
        int index = 1;
        for (String fileName : filesNames) {
            if (fileName.endsWith(IMAGE_EXTENSION)) {
                IMAGE_MAP.put(index++, new Image(getClass().getClassLoader().getResourceAsStream(fileName)));
            }
        }
    }

    /**
     * @return a map from indices to images, for passing along to the frontend in order to display image choices
     */
    Map<Integer, Image> getImageMap() { return IMAGE_MAP; }

    /**
     * Get the image associated with an index.
     *
     * @param index - the index to access
     * @return the color associated with the index
     * @throws SLogoException - in the case that the index has no valid image mapping
     */
    Image getImage(int index) throws SLogoException {
        if (!IMAGE_MAP.containsKey(index)) {
            throw new UndefinedColorException(index);
        } else {
            return IMAGE_MAP.get(index);
        }
    }
}
