package model.boost;

import control.MapCreator;
import model.GameObject;
import model.brick.SurpriseBrick;
import model.hero.Mario;

import java.awt.image.BufferedImage;

/**
 * A boost that spawn off a {@link SurpriseBrick}.
 *
 * @version 1.0.0
 */
public class Boost extends GameObject {
    private BoostType boostType;

    public Boost(double x, double y, BufferedImage style) {
        super(x, y, style);
    }

    /**
     * Sets the type of the boost based on the index of
     * the surprise brick in the map and Mario's state.
     *
     * @param position The {@link SurpriseBrick}'s index in the map.
     * @param mario    The {@link Mario} object to check its state.
     */
    public void setType(int position, Mario mario) {
        // By default, it's a coin
        boostType = BoostType.COIN;
        setStyle(MapCreator.coin);

        // In these positions it's either a mushroom or a flower depending on Mario's state
        if (position == 44 || position == 174 || position == 248) {
            if (mario.isSuper() || mario.isFire()) {
                boostType = BoostType.FIRE_FLOWER;
                setStyle(MapCreator.fireFlower);
                setVelX(0);
            } else {
                boostType = BoostType.SUPER_MUSHROOM;
                setStyle(MapCreator.superMushroom);
                setVelX(3);
            }
        }
        // There is only a star
        if (position == 230) {
            boostType = BoostType.STAR;
            setStyle(MapCreator.star);
            setVelX(3);
        }
        // And only one heart mushroom
        if (position == 148) {
            boostType = BoostType.HEART_MUSHROOM;
            setStyle(MapCreator.heartMushroom);
            setVelX(3);
        }
    }

    /* ---------- Getters / Setters ---------- */

    public BoostType getBoostType() {
        return boostType;
    }
}
