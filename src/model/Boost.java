package model;

import java.awt.image.BufferedImage;

import control.MapCreator;

public class Boost extends GameObject{

	private BoostType type;
	private MapCreator mapCreator;
	
	private BufferedImage superMushroom, fireFlower, starMan, mushroom1Up;
	
	public Boost(double x, double y, BufferedImage style) {
		super(x, y, style);
		setVelX(3);
	}
	
	public void setType() {
		this.superMushroom = mapCreator.getSuperMushroom();
		this.fireFlower = mapCreator.getFireFlower();
		this.starMan = mapCreator.getStarman();
		this.mushroom1Up = mapCreator.getMushroom1Up();
	}
}
