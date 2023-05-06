package model.hero;

import model.GameObject;

import java.awt.image.BufferedImage;

public class Fireball extends GameObject {
	
    public Fireball(BufferedImage style, double x, double y, boolean toRight) {
        super(x, y, style);
        if(toRight) {
        	setVelX(3);
        }else {
        	setVelX(-3);
        }
        setVelY(5);
    }
}
