package model.hero;

import model.GameObject;

import java.awt.image.BufferedImage;

/**
 * A fireball fired by Mario's fire form.
 *
 * @version 1.0.0
 * @see GameObject
 */
public class Fireball extends GameObject {
    public Fireball(BufferedImage style, double x, double y, boolean toRight) {
        super(x, y, style);
        setVelX(toRight ? 3 : -3);
        setVelY(5);
    }
}
