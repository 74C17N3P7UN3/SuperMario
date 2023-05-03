package model.hero;

import control.Camera;
import control.GameEngine;
import control.MapManager;
import model.GameObject;
import view.Animation;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The main character of the game.
 *
 * @author TacitNeptune
 * @version 0.1.0
 */
public class Mario extends GameObject {
    private MarioForm marioForm;
    private Animation animation;
    private boolean invincible, toRight;

    public Mario(double x, double y) {
        super(x, y, null);
        invincible = false;
        toRight = true;
        setMarioMini();
    }

    /**
     * Draws the correct {@link MarioForm} depending
     * on its state and facing direction.
     *
     * @param graphics The parent responsible for
     *                 drawing the child object.
     */
    @Override
    public void drawObject(Graphics graphics) {
        boolean movingInX = getVelX() != 0;
        boolean movingInY = getVelY() != 0;

        setStyle(marioForm.getCurrentStyle(toRight, movingInX, movingInY));
        super.drawObject(graphics);
    }

    /**
     * Makes Mario jump vertically.
     *
     * @param engine The {@link GameEngine}.
     */
    public void jump(GameEngine engine) {
        if (!isFalling() && !isJumping()) {
            setJumping(true);
            setVelY(13);
            engine.playSound("jump");
        }
    }

    /**
     * Moves Mario horizontally. It also prevents him
     * from returning back past the camera view.
     *
     * @param toRight If Mario is walking to the right.
     * @param camera  The {@link Camera} object that is
     *                rendering the scene.
     */
    public void move(boolean toRight, Camera camera) {
        if (toRight) setVelX(5);
        else if (camera.getX() < getX() - 96) setVelX(-5);

        this.toRight = toRight;
    }

    /**
     * Resets the position of Mario to the initial value.
     */
    public void resetLocation() {
        setVelX(0);
        setVelY(0);
        setX(50);
        setFalling(true);
        setJumping(false);
    }

    public void setMarioMini() {
        setDimension(48, 48);

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SMALL);

        this.animation = new Animation(leftFrames, rightFrames);
        marioForm = new MarioForm(animation, false, false,false,false);
        setStyle(marioForm.getCurrentStyle(toRight, false, false));
    }

    public void setMarioBig() {
        setDimension(96, 48);

        ImageLoader imageLoader = new ImageLoader();

        if(this.isStar()) {
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.STAR);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.STAR);
            this.animation = new Animation(leftFrames, rightFrames);
            marioForm = new MarioForm(animation, true, false, true,false);
        }else {
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SUPER);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SUPER);
            this.animation = new Animation(leftFrames, rightFrames);
            marioForm = new MarioForm(animation, true, false, false,false);
        }

        setStyle(marioForm.getCurrentStyle(toRight, false, false));
    }

    public void setMarioFire() {
        //setDimension(96, 48);

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.FIRE);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.FIRE);

        this.animation = new Animation(leftFrames, rightFrames);
        marioForm = new MarioForm(animation, false, true , false,false);
        setStyle(marioForm.getCurrentStyle(toRight, false, false));
    }
    
    public void setMarioStar() {
        ImageLoader imageLoader = new ImageLoader();

        if(this.isSuper()) {
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.STAR);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.STAR);
            this.animation = new Animation(leftFrames, rightFrames);
            marioForm = new MarioForm(animation, true, false, true,false);
        }else {
            BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.star);
            BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.star);
            this.animation = new Animation(leftFrames, rightFrames);
            marioForm = new MarioForm(animation, false, false, false,true);
        }

        setStyle(marioForm.getCurrentStyle(toRight, false, false));
    }

	public void fire(MapManager mapManager) {
		Fireball fireball = new Fireball(mapManager.getFireball(), getX(), getY(), true);
		mapManager.addFireball(fireball);
	}

    /* ---------- Getters / Setters ---------- */

    public MarioForm getMarioForm() {
        return marioForm;
    }

    public void setMarioForm(MarioForm marioForm) {
        this.marioForm = marioForm;
    }

    public boolean isFire() {
        return marioForm.isFire();
    }

    public boolean isSuper() {
        return marioForm.isSuper();
    }

    public Animation getAnimation() {
        return animation;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public boolean isStar (){return marioForm.isStar();}

    public boolean isBabyStar(){return marioForm.isBabyStar();}

}
