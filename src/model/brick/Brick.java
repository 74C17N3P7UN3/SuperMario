package model.brick;

import model.GameObject;

import java.awt.image.BufferedImage;

/**
 * Parent abstract class used to
 * define all brick-type blocks.
 *
 * @version 1.0.0
 * @see GameObject
 */
public abstract class Brick extends GameObject {
    public Brick(double x, double y, BufferedImage style) {
        super(x, y, style);
        setDimension(style.getHeight(), style.getWidth());
    }
}
