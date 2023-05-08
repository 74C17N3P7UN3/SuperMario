package model.boost;

import control.MapCreator;
import model.GameObject;
import model.hero.Mario;

import java.awt.image.BufferedImage;

// FIXME: Calza Reminder
public class Boost extends GameObject {

    private BoostType type;

    public Boost(double x, double y, BufferedImage style) {
        super(x, y, style);
    }

    public void setType(int n, Mario mario) {
        if (n == 32 || n == 47 || n == 51 || n == 213 || n == 214 || n == 241 || n == 249 || n == 256 || n == 296 || n == 300 || n == 427) {
            type = BoostType.COIN;
            setStyle(MapCreator.coin);
        }
        if (n == 44 || n == 174 || n == 248) {
            if (mario.getMarioForm().isSuper() || mario.getMarioForm().isFire()) {
                type = BoostType.FIRE_FLOWER;
                setStyle(MapCreator.fireFlower);
                setVelX(0);
            } else {
                type = BoostType.SUPER_MUSHROOM;
                setStyle(MapCreator.superMushroom);
                setVelX(3);
            }
        }
        if (n == 230) {
            type = BoostType.STAR;
            setStyle(MapCreator.star);
            setVelX(3);
        }
        if (n == 148) {
            type = BoostType.HEART_MUSHROOM;
            setStyle(MapCreator.heartMushroom);
            setVelX(3);
        }
    }

    public BoostType getType() {
        return type;
    }
}
