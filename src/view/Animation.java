package view;

import java.awt.image.BufferedImage;

/**
 * Defines the properties to animate a certain sprite.
 * The sprite is handled by the calling class, and this
 * only calculates the right or left frames, depending
 * on the passed direction, at a given speed.
 *
 * @version 1.0.0
 */
public class Animation {
    private int count = 0, index = 0;
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
     * @param speed         The speed of the played animation.
     *                      The higher the speed, the lower
     *                      the playing time of the animation.
     * @param toRight       If the animation should be played
     *                      to the right (in sequence) or not
     *                      (in reverse).
     * @param animationStar The current state of Mario's star
     *                      form animation.
     * @return The current frame that needs to be rendered
     * of the full animation.
     */
    public BufferedImage animate(int speed, boolean toRight, int animationStar) {
        count++;
        BufferedImage[] frames = toRight ? rightFrames : leftFrames;

        if (count > speed) {
            nextFrame(frames, animationStar);
            count = 0;
        }

        return currentFrame;
    }

    /**
     * Utility method to calculate the next frame in the
     * playing sequence, keeping count of the number of
     * frames before restarting the animation.
     *
     * @param frames        The frames of the animation sequence.
     * @param animationStar The current state of Mario's star
     *                      form animation.
     */
    private void nextFrame(BufferedImage[] frames, int animationStar) {
        if (index + 2 >= 5) index = 0;

        if (animationStar == 0) currentFrame = frames[index + 2];
        if (animationStar == 1) currentFrame = frames[index + 7];
        if (animationStar == 2) currentFrame = frames[index + 12];
        if (animationStar == 3) currentFrame = frames[index + 17];

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
