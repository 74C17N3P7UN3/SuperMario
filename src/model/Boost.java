package model;

import java.awt.image.BufferedImage;

import control.MapCreator;

public class Boost extends GameObject{

	private BoostType type;
	private MapCreator mapCreator;
	
	private BufferedImage superMushroom, fireFlower, starMan, mushroom1Up	;
	
	public Boost(double x, double y, BufferedImage style) {
		super(x, y, style);
		this.superMushroom = mapCreator.getSuperMushroom();
		this.fireFlower = mapCreator.getFireFlower();
		this.starMan = mapCreator.getStar();
		this.mushroom1Up = mapCreator.getHeartMushroom();
		
	}
	
	public void setType() {
		type = BoostType.superMushroom;
		this.setStyle(superMushroom);
	}
	
	public BoostType getType(){
		return this.type;
	}
}
