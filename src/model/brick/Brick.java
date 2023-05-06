package model.brick;

import model.GameObject;

import java.awt.image.BufferedImage;

public abstract class Brick extends GameObject {
    private boolean breakable;
    private boolean empty;

    public Brick(double x, double y, BufferedImage style) {
        super(x, y, style);
        setDimension(style.getHeight(), style.getWidth());
    }

    /* ---------- Getters / Setters ---------- */

    public boolean isBreakable() {
        return breakable;
    }

    public void setBreakable(boolean breakable) {
        this.breakable = breakable;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
