package view;

import utils.ImageImporter;

import java.awt.image.BufferedImage;

/**
 * Provides basic methods for loading images
 * and getting specific frames or parts of
 * the animated sprite texture from a png.
 *
 * @author Bartolomuwu
 * @version 0.1.0
 */
public class ImageLoader {
    private BufferedImage marioForms;
    private BufferedImage brickAnimation;

    public ImageLoader() {
        marioForms = ImageImporter.loadImage("mario-forms");
        brickAnimation = ImageImporter.loadImage("brick-animation");
    }

    public BufferedImage getImage(BufferedImage image, int x, int y, int w, int h) {
        if ((x == 1 || x == 4) && y == 3)
            return image.getSubimage((x - 1) * 48, 128, w, h);
        return image.getSubimage((x - 1) * 48, (y - 1) * 48, w, h);
    }

    public BufferedImage[] getLeftFrames(int marioForm) {
        BufferedImage[] leftFrames = new BufferedImage[5];
        int col = 1;
        int width = 52, height = 48;

        if (marioForm == 1) {
            col = 4;
            width = 48;
            height = 96;
        } else if (marioForm == 2) {
            col = 7;
            width = 48;
            height = 96;
        }

        for (int i = 0; i < 5; i++)
            leftFrames[i] = marioForms.getSubimage((col - 1) * width, i * height, width, height);
        return leftFrames;
    }

    public BufferedImage[] getRightFrames(int marioForm) {
        BufferedImage[] rightFrames = new BufferedImage[5];
        int col = 2;
        int width = 52, height = 48;

        if (marioForm == 1) {
            col = 5;
            width = 48;
            height = 96;
        } else if (marioForm == 2) {
            col = 8;
            width = 48;
            height = 96;
        }

        for (int i = 0; i < 5; i++)
            rightFrames[i] = marioForms.getSubimage((col - 1) * width, (i) * height, width, height);
        return rightFrames;
    }

    public BufferedImage[] getBrickFrames() {
        BufferedImage[] frames = new BufferedImage[4];
        for (int i = 0; i < 4; i++)
            frames[i] = brickAnimation.getSubimage(i * 105, 0, 105, 105);
        return frames;
    }
}
