package model.brick;

import java.awt.image.BufferedImage;

/**
 * A brick on the ground.
 *
 * @version 1.0.0
 * @see Brick
 */
public class GroundBrick extends Brick {
    public GroundBrick(double x, double y, BufferedImage style) {
        super(x, y, style);
    }
}
