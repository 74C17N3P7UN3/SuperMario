package model.brick;

import java.awt.image.BufferedImage;

/**
 * A brick floating in the sky.
 *
 * @version 1.0.0
 * @see Brick
 */
public class OrdinaryBrick extends Brick {
    public OrdinaryBrick(double x, double y, BufferedImage style) {
        super(x, y, style);
    }
}
