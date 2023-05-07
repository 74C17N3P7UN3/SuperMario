package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Objects;

/**
 * Util class responsible for importing images
 * from the media's package by their name.
 *
 * @version 1.0.0
 */
public class ImageImporter {
    /**
     * Returns the {@link BufferedImage} of a given image
     * by its relative name inside the media's package.
     *
     * @param imageName The name of the image to be loaded.
     * @return The {@link BufferedImage} of the requested image.
     */
    public static BufferedImage loadImage(String imageName) {
        return Objects.requireNonNull(getImage("images/" + imageName));
    }

    /**
     * Returns the {@link BufferedImage} of the logo.
     *
     * @return The {@link BufferedImage} of the logo.
     */
    public static BufferedImage loadLogo() {
        return Objects.requireNonNull(getImage("logo"));
    }

    /**
     * Returns the {@link BufferedImage} of a given map
     * by its relative name inside the media's package.
     *
     * @param mapName The name of the map to be loaded.
     * @return The {@link BufferedImage} of the requested map.
     */
    public static BufferedImage loadMap(String mapName) {
        return Objects.requireNonNull(getImage("maps/" + mapName));
    }

    /**
     * Returns the {@link BufferedImage} of a given image
     * by its relative name inside the media's package.
     *
     * @param name The name of the image to be loaded.
     * @return The {@link BufferedImage} of the requested image.
     */
    private static BufferedImage getImage(String name) {
        try {
            URL url = ImageImporter.class.getResource("/media/" + name + ".png");
            return ImageIO.read(Objects.requireNonNull(url));
        } catch (Exception ignored) {}

        return null;
    }
}
