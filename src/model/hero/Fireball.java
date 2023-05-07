package model.hero;

import model.GameObject;

import java.awt.image.BufferedImage;

public class Fireball extends GameObject {

    public Fireball(BufferedImage style, double x, double y, boolean toRight) {
        super(x, y, style);
        setVelX(toRight ? 3 : -3);
        setVelY(5);
    }
}
