package model;

import java.awt.image.BufferedImage;

/**
 * The flag at the end of the level.
 *
 * @version 1.0.0
 * @see GameObject
 */
public class EndFlag extends GameObject {
    private boolean touched;

    public EndFlag(double x, double y, BufferedImage style) {
        super(x, y, style);
        touched = false;
    }

    /**
     * Updates the location of the sprite given its status.
     * Provides checks for vertical conditions and updates
     * the horizontal position regardless every game tick.
     */
    @Override
    public void updateLocation() {
        if (touched) {
            if (getY() + getDimension().getHeight() >= 528) {
                setFalling(false);
                setVelY(0);
                setY(528);
            }

            super.updateLocation();
        }
    }

    /* ---------- Getters / Setters ---------- */

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}
