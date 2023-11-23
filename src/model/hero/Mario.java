package model.hero;

import control.Camera;
import control.GameEngine;
import control.MapManager;
import model.GameObject;
import net.Packet;
import view.Animation;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The main character of the game.
 *
 * @version 1.0.0
 * @see GameObject
 */
public class Mario extends GameObject {
    private Animation animation;
    private MarioForm marioForm;
    private boolean firing, invincible, toRight;
    private final String username;

    public Mario(double x, double y, String username) {
        super(x, y, null);
        this.username = username;

        invincible = false;
        toRight = true;
        setMarioSmall();
    }

    /**
     * Draws the correct {@link MarioForm} depending
     * on its state and facing direction.
     *
     * @param g2D The graphics engine.
     */
    @Override
    public void drawObject(Graphics2D g2D) {
        boolean movingInX = getVelX() != 0;
        boolean movingInY = getVelY() != 0;

        setStyle(marioForm.getCurrentStyle(toRight, movingInX, movingInY));
        super.drawObject(g2D);
    }

    /**
     * Makes Mario jump vertically.
     */
    public void jump() {
        if (!isFalling() && !isJumping()) {
            setJumping(true);
            setVelY(13);
            GameEngine.playSound("jump");
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
     * Sets Mario to his normal form.
     */
    public void setMarioSmall() {
        setDimension(48, 48);

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SMALL);

        animation = new Animation(leftFrames, rightFrames);
        marioForm = new MarioForm(animation, false, false, false, false);
        setStyle(marioForm.getCurrentStyle(toRight, false, false));
    }

    /**
     * Sets Mario to his super form.
     */
    public void setMarioSuper() {
        setDimension(96, 48);

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(isBabyStar() ? MarioForm.STAR : MarioForm.SUPER);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(isBabyStar() ? MarioForm.STAR : MarioForm.SUPER);

        animation = new Animation(leftFrames, rightFrames);
        marioForm = new MarioForm(animation, true, false, isBabyStar(), false);
        setStyle(marioForm.getCurrentStyle(toRight, false, false));
    }

    /**
     * Sets Mario to his fire form.
     */
    public void setMarioFire() {
        ImageLoader imageLoader = new ImageLoader();

        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.FIRE);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.FIRE);

        animation = new Animation(leftFrames, rightFrames);
        marioForm = new MarioForm(animation, true, true, false, false);
        setStyle(marioForm.getCurrentStyle(toRight, false, false));
    }

    /**
     * Sets Mario to his baby or normal star form.
     */
    public void setMarioStar() {
        ImageLoader imageLoader = new ImageLoader();

        BufferedImage[] leftFrames = imageLoader.getLeftFrames(isSuper() ? MarioForm.STAR : MarioForm.BABY_STAR);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(isSuper() ? MarioForm.STAR : MarioForm.BABY_STAR);

        animation = new Animation(leftFrames, rightFrames);
        marioForm = new MarioForm(animation, isSuper(), isFire(), isSuper(), !isSuper());
        setStyle(marioForm.getCurrentStyle(toRight, false, false));
    }

    /**
     * Shoots a fireball depending on the direction.
     *
     * @param mapManager The {@link MapManager} needed to
     *                   spawn the fireball in the map.
     */
    public void fire(MapManager mapManager) {
        mapManager.getMap().getFireballs().add(marioForm.fire(toRight, getX(), getY()));
        GameEngine.playSound("fireball");
    }

    /**
     * Teleports Mario when using a pipe to a target point.
     *
     * @param destinationX The destination x coordinate.
     * @param destinationY The destination y coordinate.
     */
    public void pipeTeleport(int destinationX, int destinationY) {
        setVelX(0);
        setVelY(0);
        setX(destinationX);
        setY(destinationY);
        setJumping(false);
        setFalling(true);

        GameEngine.playSound("pipe");
    }

    /**
     * Updates Mario based on the given
     * packet, received from the other player.
     *
     * @param packet The packet containing the
     *               new information about Mario.
     */
    public void updateFromPacket(Packet packet) {
        setX(packet.x);
        setY(packet.y);
        setVelX(packet.velX);
        setVelY(packet.velY);
        setFalling(packet.falling);
        setJumping(packet.jumping);

        setFiring(packet.firing);
        setInvincible(packet.invincible);
        setToRight(packet.toRight);

        Packet.PacketForm packetForm = packet.currentForm;
        if (!packetForm.isSuper() && !packetForm.isFire() && !packetForm.isStar() && !packetForm.isBabyStar()) {
            if (isSuper() || isFire() || isStar() || isBabyStar()) setMarioSmall();
        } else if (packetForm.isSuper() && !packetForm.isFire() && !packetForm.isBabyStar()) {
            if (!isSuper() || isFire() || isBabyStar()) setMarioSuper();
        } else if (packetForm.isSuper() && packetForm.isFire() && !packetForm.isStar() && !packetForm.isBabyStar()) {
            if (!isSuper() || !isFire() || isStar() || isBabyStar()) setMarioFire();
        } else setMarioStar();
    }

    /* ---------- Getters / Setters ---------- */

    public boolean isSuper() {
        return marioForm.isSuper();
    }

    public boolean isFire() {
        return marioForm.isFire();
    }

    public void setFire(boolean fire) {
        marioForm.setFire(fire);
    }

    public boolean isFiring() {
        return firing;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }

    public boolean isBabyStar() {
        return marioForm.isBabyStar();
    }

    public boolean isStar() {
        return marioForm.isStar();
    }

    public boolean isToRight() {
        return toRight;
    }

    public void setToRight(boolean toRight) {
        this.toRight = toRight;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public String getUsername() {
        return username;
    }
}
