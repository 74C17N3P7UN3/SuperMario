package model.hero;

import utils.ImageImporter;
import view.Animation;
import view.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * Defines the possible states in which Mario could be found. Those
 * states are Normal, Super and Fire, each with its animated sprite.
 *
 * @author TacitNeptune
 * @version 0.1.0
 */
public class MarioForm {
    public static final int SMALL = 0, SUPER = 1, FIRE = 2, star = 3, STAR = 4;

    private Animation animation;
    private BufferedImage fireballStyle;

    private boolean isSuper, isFire, isStar;

    public MarioForm(Animation animation, boolean isSuper, boolean isFire, boolean isStar) {
        this.animation = animation;
        this.isSuper = isSuper;
        this.isFire = isFire;
        this.isStar = isStar;

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage sprite = ImageImporter.loadImage("sprite");
        fireballStyle = imageLoader.getImage(sprite, 2, 3, 25, 22);
    }

    /**
     * Returns the style to be displayed based on the movement
     * direction and currently selected Mario variation. This
     * is based on the frame of a specified sprite state.
     *
     * @param toRight   If Mario is moving to the right.
     * @param movingInX If Mario is moving horizontally.
     * @param movingInY If Mario is moving vertically.
     * @return The frame of the style to be rendered.
     */
    public BufferedImage getCurrentStyle(boolean toRight, boolean movingInX, boolean movingInY) {
        BufferedImage style = null;

        if (movingInX) style = animation.animate(5, toRight);
        if (movingInY) style = animation.getLeftFrames()[0];
        if (movingInY && toRight) style = animation.getRightFrames()[0];
        if (!movingInX && !movingInY)
            style = toRight ? animation.getRightFrames()[1] : animation.getLeftFrames()[1];

        return style;
    }

    /**
     * Renders the new MarioForm if it touches an enemy. This is called
     * only if Mario is in one of its bigger forms, otherwise it means
     * that it was normal and has lost a life or died.
     *
     * @param imageLoader The {@link ImageLoader} responsible for returning
     *                    the frames of the new MarioForm.
     * @return The new MarioForm to be rendered.
     */
    public MarioForm onTouchEnemy(ImageLoader imageLoader) {
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(0);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(0);
        Animation newAnimation = new Animation(leftFrames, rightFrames);

        return new MarioForm(newAnimation, false, false, false);
    }

    /**
     * The Fireball that needs to be rendered when Mario is in its Fire form.
     *
     * @param toRight If the Fireball should be shot to the right or not.
     * @param x       The x coordinate from where the Fireball starts travelling.
     * @param y       The vertical coordinate of the Fireball.
     * @return The Fireball object to be rendered, if Mario is in the Fire form.
     */
    public Fireball fire(boolean toRight, double x, double y) {
        return isFire ? new Fireball(fireballStyle, x, y + 48, toRight) : null;
    }

    /* ---------- Getters / Setters ---------- */

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean isSuper) {
        this.isSuper = isSuper;
    }

    public boolean isFire() {
        return isFire;
    }
    
    public boolean isStar() {
    	return isStar;
    }

    public void setFire(boolean isFire) {
        this.isFire = isFire;
    }
    
    public void setStar(boolean isStar) {
    	this.isStar = isStar;
    }
}
