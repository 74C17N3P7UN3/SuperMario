package model.brick;

import model.GameObject;

import java.awt.image.BufferedImage;

public abstract class Brick extends GameObject {
    public Brick(double x, double y, BufferedImage style) {
        super(x, y, style);
        setDimension(style.getHeight(), style.getWidth());
    }
}
