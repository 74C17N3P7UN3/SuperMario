package model.hero;

import model.GameObject;

import java.awt.image.BufferedImage;

public class Fireball extends GameObject {
    public Fireball(BufferedImage style, double x, double y, boolean toRight) {
        super(x, y, style);
        setFalling(false);
        setJumping(false);
        setVelX(toRight ? 10 : -5);
    }
}
