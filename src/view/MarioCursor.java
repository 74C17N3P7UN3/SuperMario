package view;

import model.hero.MarioForm;

import java.awt.image.BufferedImage;

/**
 * This is the graphical cursor displayed on the selection
 * screens, for the user to interact with the interface.
 *
 * @version 1.0.0
 */
public class MarioCursor {
    private final Animation animation;

    public MarioCursor() {
        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SMALL);
        animation = new Animation(leftFrames, rightFrames);
    }

    /**
     * Animates the mario cursor.
     *
     * @return The current frame of the animated cursor.
     */
    public BufferedImage getCurrentStyle() {
        return animation.animate(5, true, 0);
    }
}
