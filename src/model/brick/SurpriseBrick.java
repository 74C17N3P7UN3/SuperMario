package model.brick;

import java.awt.image.BufferedImage;

public class SurpriseBrick extends Brick {
    private int boost;

    public SurpriseBrick(double x, double y, BufferedImage style) {
        super(x, y, style);
        boost = 1;
    }

    /* ---------- Getters / Setters ---------- */

    public int getBoost() {
        return boost;
    }

    public void setBoost(int boost) {
        this.boost = boost;
    }

    public void decrementBoosts() {
    	boost--;
    }
}
