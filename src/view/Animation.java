package view;

import java.awt.image.BufferedImage;

/**
 * Defines the properties to animate a certain sprite.
 * The sprite is handled by the calling class, and this
 * only calculates the right or left frames, depending
 * on the passed direction, at a given speed.
 *
 * @author TacitNeptune
 * @version 0.1.0
 */
public class Animation {
    private int index = 0, count = 0;
    private final BufferedImage[] leftFrames;
    private final BufferedImage[] rightFrames;
    private BufferedImage currentFrame;

    public Animation(BufferedImage[] leftFrames, BufferedImage[] rightFrames) {
        this.leftFrames = leftFrames;
        this.rightFrames = rightFrames;

        currentFrame = rightFrames[1];
    }

    /**
     * Returns the next frame of the animation sequence.
     *
     * @param speed   The speed of the played animation.
     *                The higher the speed, the lower
     *                the playing time of the animation.
     * @param toRight If the animation should be played
     *                to the right (in sequence) or not
     *                (in reverse).
     * @return The current frame that needs to be rendered
     * of the full animation.
     */
    public BufferedImage animate(int speed, boolean toRight, boolean isStar) {
        count++;
        BufferedImage[] frames = toRight ? rightFrames : leftFrames;

        if (count > speed) {
            nextFrame(frames, isStar);
            count = 0;
        }

        return currentFrame;
    }

    /**
     * Utility method to calculate the next frame in the
     * playing sequence, keeping count of the number of
     * frames before restarting the animation.
     *
     * @param frames The frames of the animation sequence.
     */
    private void nextFrame(BufferedImage[] frames, boolean isStar) {
        if (index + 2 >= frames.length) index = 0;

        currentFrame = frames[index + 2];
        index++;
    }

    /* ---------- Getters / Setters ---------- */

    public BufferedImage[] getLeftFrames() {
        return leftFrames;
    }

    public BufferedImage[] getRightFrames() {
        return rightFrames;
    }
}
