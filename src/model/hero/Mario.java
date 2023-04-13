package model.hero;

import control.Camera;
import control.GameEngine;
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

    private boolean toRight;

    public Mario(double x, double y) {
        super(x, y, null);
        setDimension(48, 48);
        toRight = true;

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SMALL);

        Animation animation = new Animation(leftFrames, rightFrames);
        marioForm = new MarioForm(animation, false, false);
        setStyle(marioForm.getCurrentStyle(toRight, false, false));
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
            setVelY(10);
            // TODO: engine.playSound("jump");
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
        else if (camera.getX() < getX()) setVelX(-5);

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

    /* ---------- Getters / Setters ---------- */

    public MarioForm getMarioForm() {
        return marioForm;
    }

    public void setMarioForm(MarioForm marioForm) {
        this.marioForm = marioForm;
    }

    public boolean isSuper() {
        return marioForm.isSuper();
    }

    public boolean isToRight() {
        return toRight;
    }
}
