package model;

import java.awt.image.BufferedImage;

import control.MapCreator;

public class Boost extends GameObject{

	private BoostType type;
	
	public Boost(double x, double y, BufferedImage style) {
		super(x, y, style);
	}
	
	public void setType(MapCreator mapCreator, int n) {
		if(n==32 || n==47 || n==51 || n==213 /*n==213*/ || n==241 || n==249 || n==256 || n==296 || n==300 || n==427) {
			type = BoostType.money;
			setStyle(mapCreator.getMoney());
		}
		if(n==44 || n==174 || n==248) {
			type = BoostType.superMushroom;
			setStyle(mapCreator.getSuperMushroom());
			setVelX(3);
		}
		if(n==230){
			type = BoostType.starMan;
			setStyle(mapCreator.getStarMan());
			setVelX(3);
		}
		if(n==148){
			type = BoostType.mushroom1Up;
			setStyle(mapCreator.getMushroom1Up());
			setVelX(3);
		}
	}
	
	public BoostType getType() {
		return type;
	}
}
