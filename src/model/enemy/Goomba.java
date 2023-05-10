package model.enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A goomba enemy.
 *
 * @version 1.0.0
 * @see Enemy
 */
public class Goomba extends Enemy {
    private BufferedImage rightImage;

    public Goomba(double x, double y, BufferedImage style) {
        super(x, y, style);
        setVelX(0);
    }

    /**
     * Draws the {@link BufferedImage} stored in the class
     * at the {@code (x, y)} coordinates with the given
     * {@link Graphics} parent drawer class.
     *
     * @param g2D The graphics engine.
     */
    @Override
    public void drawObject(Graphics2D g2D) {
        if (getVelX() > 0) g2D.drawImage(rightImage, (int) getX(), (int) getY(), null);
        else super.drawObject(g2D);
    }

    /* ---------- Getters / Setters ---------- */

    public void setRightImage(BufferedImage rightImage) {
        this.rightImage = rightImage;
    }
}
