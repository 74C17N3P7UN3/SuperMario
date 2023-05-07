package model.brick;

import java.awt.image.BufferedImage;

public class SurpriseBrick extends Brick {
    private boolean boost;

    public SurpriseBrick(double x, double y, BufferedImage style) {
        super(x, y, style);
        setBoost(true);
    }

    /* ---------- Getters / Setters ---------- */

    public boolean isBoost() {
        return boost;
    }

    public void setBoost(boolean boost) {
        this.boost = boost;
    }
}
