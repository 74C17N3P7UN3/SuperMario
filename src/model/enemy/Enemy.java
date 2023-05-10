package model.enemy;

import model.GameObject;

import java.awt.image.BufferedImage;

/**
 * Parent abstract class used to
 * define all the enemy types.
 *
 * @version 1.0.0
 * @see GameObject
 */
public abstract class Enemy extends GameObject {
    public Enemy(double x, double y, BufferedImage style) {
        super(x, y, style);
        setFalling(false);
        setJumping(false);
    }
}
