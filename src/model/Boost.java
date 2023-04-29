package model;

import java.awt.image.BufferedImage;

import control.MapCreator;

public class Boost extends GameObject{

	private BoostType type;
	private MapCreator mapCreator;
	private int[] positions = {32,44,47,51,148,174,213,214,241,248,249,256,296,300,427};
	
	private BufferedImage superMushroom, fireFlower, starMan, mushroom1Up;
	
	public Boost(double x, double y, BufferedImage style) {
		super(x, y, style);
		setVelX(3);
	}
	
	public void setType(int n) {
		this.superMushroom = mapCreator.getSuperMushroom();
		this.fireFlower = mapCreator.getFireFlower();
		this.starMan = mapCreator.getStarman();
		this.mushroom1Up = mapCreator.getMushroom1Up();
	}
}
