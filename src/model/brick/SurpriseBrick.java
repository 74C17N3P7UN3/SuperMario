package model.brick;

import java.awt.image.BufferedImage;

public class SurpriseBrick extends Brick {
	
	private boolean boost;
	
    public SurpriseBrick(double x, double y, BufferedImage style) {
        super(x, y, style);
        setBoost(true);
    }
    
    public void setBoost(boolean boost) {
    	this.boost=boost;
    }
    public boolean getBoost() {
    	return this.boost;
    }
}
