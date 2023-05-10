package model.hero;

import control.MapCreator;
import view.Animation;

import java.awt.image.BufferedImage;

/**
 * Defines the possible states in which Mario could be found. Those
 * states are Small, Super, Fire and Star each with its animated sprite.
 *
 * @version 1.0.0
 */
public class MarioForm {
    public static final int SMALL = 0, SUPER = 1, FIRE = 2, BABY_STAR = 3, STAR = 4;

    private final Animation animation;
    private int starAnimation;

    private boolean isSuper, isFire;
    private final boolean isBabyStar, isStar;

    public MarioForm(Animation animation, boolean isSuper, boolean isFire, boolean isStar, boolean isBabyStar) {
        this.animation = animation;
        this.isSuper = isSuper;
        this.isFire = isFire;
        this.isStar = isStar;
        this.isBabyStar = isBabyStar;
        this.starAnimation = 0;
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

        if (isBabyStar || isStar) {
            if (starAnimation < 20) starAnimation++;
            else if (starAnimation == 20) starAnimation = 0;
        }

        if (starAnimation < 5) {
            if (movingInX) style = animation.animate(5, toRight, 0);
            if (movingInY) style = animation.getLeftFrames()[0];
            if (movingInY && toRight) style = animation.getRightFrames()[0];
            if (!movingInX && !movingInY) style = toRight ? animation.getRightFrames()[1] : animation.getLeftFrames()[1];
        } else if (starAnimation < 10) {
            if (movingInX) style = animation.animate(5, toRight, 1);
            if (movingInY) style = animation.getLeftFrames()[5];
            if (movingInY && toRight) style = animation.getRightFrames()[5];
            if (!movingInX && !movingInY) style = toRight ? animation.getRightFrames()[6] : animation.getLeftFrames()[6];
        } else if (starAnimation < 15) {
            if (movingInX) style = animation.animate(5, toRight, 2);
            if (movingInY) style = animation.getLeftFrames()[10];
            if (movingInY && toRight) style = animation.getRightFrames()[10];
            if (!movingInX && !movingInY) style = toRight ? animation.getRightFrames()[11] : animation.getLeftFrames()[11];
        } else {
            if (movingInX) style = animation.animate(5, toRight, 3);
            if (movingInY) style = animation.getLeftFrames()[15];
            if (movingInY && toRight) style = animation.getRightFrames()[15];
            if (!movingInX && !movingInY) style = toRight ? animation.getRightFrames()[16] : animation.getLeftFrames()[16];
        }

        return style;
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
        return new Fireball(MapCreator.fireball, x, y + 48, toRight);
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

    public void setFire(boolean isFire) {
        this.isFire = isFire;
    }

    public boolean isBabyStar() {
        return isBabyStar;
    }

    public boolean isStar() {
        return isStar;
    }
}
