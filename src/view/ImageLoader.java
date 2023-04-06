package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Provides basic methods for loading images
 * and getting specific frames or parts of
 * the animated sprite texture from a png.
 *
 * @author Bartolomuwu
 * @version 0.1.0
 */
public class ImageLoader {
    public ImageLoader() {

    }

    // TODO: Javadoc
    public BufferedImage loadImage(String name) {
        BufferedImage imageToReturn = null;

        try { // TODO: Create a utility class that handles nonsense exceptions
            imageToReturn = ImageIO.read(getClass().getResource("/media/images" + name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageToReturn;
    }

    // TODO: Javadoc
    public BufferedImage loadImage(File file) {
        BufferedImage imageToReturn = null;

        try {
            imageToReturn = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageToReturn;
    }

    // TODO: Javadoc
    public BufferedImage getImage(BufferedImage image, int x, int y, int w, int h) {
        if ((x == 1 || x == 4) && y == 3)
            return image.getSubimage((x - 1) * 48, 128, w, h);
        return image.getSubimage((x - 1) * 48, (y - 1) * 48, w, h);
    }
}
