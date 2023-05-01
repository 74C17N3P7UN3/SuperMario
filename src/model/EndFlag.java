package model;

import java.awt.image.BufferedImage;

public class EndFlag extends GameObject {
    private boolean touched;

    public EndFlag(double x, double y, BufferedImage style) {
        super(x, y, style);
        touched = false;
    }

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

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}
