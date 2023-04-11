package model.brick;

import control.GameEngine;
import model.GameObject;
import model.prize.Prize;

import java.awt.image.BufferedImage;

public abstract class Brick extends GameObject {
    private boolean breakable;
    private boolean empty;

    public Brick(double x, double y, BufferedImage style) {
        super(x, y, style);
        setDimension(48, 48);
    }

    public Prize getPrize() {
        return null;
    }

    public Prize reveal(GameEngine engine) {
        return null;
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
