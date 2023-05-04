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

    private boolean isSuper, isFire, isStar, isBabyStar;
    private int starAnimation;

    public MarioForm(Animation animation, boolean isSuper, boolean isFire,boolean isStar, boolean isBabyStar) {
        this.animation = animation;
        this.isSuper = isSuper;
        this.isFire = isFire;
        this.isStar = isStar;
        this.isBabyStar = isBabyStar;
        this.starAnimation = 0;

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
        
        //TODO: ottimizzare sta roba
        if(isBabyStar || isStar) {
        	if(starAnimation == 0) {
        		if (movingInX) style = animation.animate(5, toRight);
                if (movingInY) style = animation.getLeftFrames()[0];
                if (movingInY && toRight) style = animation.getRightFrames()[0];
                if (!movingInX && !movingInY)
                    style = toRight ? animation.getRightFrames()[1] : animation.getLeftFrames()[1];
                starAnimation++;
        	}
        	if(starAnimation == 1) {
        		if (movingInX) style = animation.animate(5, toRight);
                if (movingInY) style = animation.getLeftFrames()[5];
                if (movingInY && toRight) style = animation.getRightFrames()[5];
                if (!movingInX && !movingInY)
                    style = toRight ? animation.getRightFrames()[6] : animation.getLeftFrames()[6];
                starAnimation--;
        	}
        }else {
            if (movingInX) style = animation.animate(5, toRight);
            if (movingInY) style = animation.getLeftFrames()[0];
            if (movingInY && toRight) style = animation.getRightFrames()[0];
            if (!movingInX && !movingInY)
                style = toRight ? animation.getRightFrames()[1] : animation.getLeftFrames()[1];
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

    public void setFire(boolean isFire) {
        this.isFire = isFire;
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean isStar) {
        this.isStar = isStar;
    }

    public boolean isBabyStar() {
        return isBabyStar;
    }

    public void setBabyStar(boolean isBabyStar) {
        this.isBabyStar = isBabyStar;
    }
}
