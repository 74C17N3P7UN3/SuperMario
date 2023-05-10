package view;

import model.hero.MarioForm;
import utils.ImageImporter;

import java.awt.image.BufferedImage;

/**
 * Provides basic methods for loading images
 * and getting specific frames or parts of
 * the animated sprite texture from a png.
 *
 * @version 1.0.0
 */
public class ImageLoader {
    private final BufferedImage marioForms;

    public ImageLoader() {
        marioForms = ImageImporter.loadImage("mario-forms");
    }

    /**
     * Returns a sub-image of a given bigger one.
     *
     * @param image The referenced initial image.
     * @param x     The starting left x coordinate.
     * @param y     The starting top y coordinate.
     * @param h     The height of the sub-image.
     * @param w     The width of the sub-image.
     * @return The cropped sub-image of the given one.
     */
    public static BufferedImage getImage(BufferedImage image, int x, int y, int h, int w) {
        return image.getSubimage(x * 48, y * 48, w, h);
    }

    /**
     * Returns the left-oriented frames
     * of a given {@link MarioForm}
     *
     * @param marioForm The currently active {@link MarioForm}
     * @return The array with the left frames
     */
    public BufferedImage[] getLeftFrames(int marioForm) {
        int frames = (marioForm == MarioForm.BABY_STAR || marioForm == MarioForm.STAR) ? 20 : 5;
        BufferedImage[] leftFrames = new BufferedImage[frames];

        int col = 1;
        int height = 48, width = 48;

        if (marioForm == MarioForm.SUPER || marioForm == MarioForm.STAR) {
            col = 4;
            height = 96;
        } else if (marioForm == MarioForm.FIRE) {
            col = 7;
            height = 96;
        }

        // The original frames
        for (int i = 0; i < 5; i++)
            leftFrames[i] = marioForms.getSubimage((col - 1) * 48, i * height, width, height);
        // The added frames if Mario is star
        if (marioForm == MarioForm.BABY_STAR || marioForm == MarioForm.STAR) {
            int count = 5;
            for (int n = 0; n < 3; n++)
                for (int i = 0; i < 5; i++)
                    leftFrames[count++] = marioForms.getSubimage((col + 8 + n * 6) * 48, i * height, width, height);
        }

        return leftFrames;
    }

    /**
     * Returns the right-oriented frames
     * of a given {@link MarioForm}
     *
     * @param marioForm The currently active {@link MarioForm}
     * @return The array with the right frames
     */
    public BufferedImage[] getRightFrames(int marioForm) {
        int frames = (marioForm == MarioForm.BABY_STAR || marioForm == MarioForm.STAR) ? 20 : 5;
        BufferedImage[] rightFrames = new BufferedImage[frames];

        int col = 2;
        int width = 48, height = 48;

        if (marioForm == MarioForm.SUPER || marioForm == MarioForm.STAR) {
            col = 5;
            height = 96;
        } else if (marioForm == MarioForm.FIRE) {
            col = 8;
            height = 96;
        }

        // The original frames
        for (int i = 0; i < 5; i++)
            rightFrames[i] = marioForms.getSubimage((col - 1) * 48, i * height, width, height);
        // The added frames if Mario is star
        if (marioForm == MarioForm.BABY_STAR || marioForm == MarioForm.STAR) {
            int count = 5;
            for (int n = 0; n < 3; n++)
                for (int i = 0; i < 5; i++)
                    rightFrames[count++] = marioForms.getSubimage((col + 8 + n * 6) * 48, i * height, width, height);
        }

        return rightFrames;
    }
}
