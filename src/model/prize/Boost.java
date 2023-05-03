package model.prize;

import java.awt.image.BufferedImage;

import control.MapCreator;
import model.GameObject;
import model.hero.Mario;

public class Boost extends GameObject {

	private BoostType type;

	public Boost(double x, double y, BufferedImage style) {
		super(x, y, style);
	}

	public void setType(MapCreator mapCreator, int n, Mario mario) {
		if(n==32 || n==47 || n==51 || n==213 || n==241 || n==249 || n==256 || n==296 || n==300 || n==427) {
			type = BoostType.money;
			setStyle(mapCreator.getCoin());
		}
		if(n==44 || n==174 || n==248) {
			if(mario.getMarioForm().isSuper()) {
				type = BoostType.fireFlower;
				setStyle(mapCreator.getFireFlower());
				setVelX(0);
			}else {
				type = BoostType.superMushroom;
				setStyle(mapCreator.getSuperMushroom());
				setVelX(3);
			}
		}
		if(n==230){
			type = BoostType.starMan;
			setStyle(mapCreator.getStar());
			setVelX(3);
		}
		if(n==148){
			type = BoostType.mushroom1Up;
			setStyle(mapCreator.getHeartMushroom());
			setVelX(3);
		}
	}

	public BoostType getType() {
		return type;
	}
}
