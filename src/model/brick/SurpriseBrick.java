package model.brick;

import java.awt.image.BufferedImage;

/**
 * A surprise brick which contains one or more boosts
 * that get revealed when Mario breaks the block.
 *
 * @version 1.0.0
 * @see Brick
 */
public class SurpriseBrick extends Brick {
    private int boostsAmount;

    public SurpriseBrick(double x, double y, BufferedImage style) {
        super(x, y, style);
        boostsAmount = 1;
    }

    /* ---------- Getters / Setters ---------- */

    public int getBoostsAmount() {
        return boostsAmount;
    }

    public void setBoostsAmount(int boostsAmount) {
        this.boostsAmount = boostsAmount;
    }

    public void decrementBoosts() {
        boostsAmount--;
    }
}
