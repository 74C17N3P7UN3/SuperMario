package model.enemy;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Goomba extends Enemy {
    private BufferedImage rightImage;

    public Goomba(double x, double y, BufferedImage style) {
        super(x, y, style);
        setVelX(-3);
    }

    @Override
    public void drawObject(Graphics g) {
        if (getVelX() > 0) g.drawImage(rightImage, (int) getX(), (int) getY(), null);
        else super.drawObject(g);
    }

    /* ---------- Getters / Setters ---------- */

    public void setRightImage(BufferedImage rightImage) {
        this.rightImage = rightImage;
    }
}
