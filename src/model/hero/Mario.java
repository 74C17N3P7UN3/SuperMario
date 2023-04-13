package model.hero;

import model.GameObject;

public class Mario extends GameObject {
    public Mario(double x, double y) {
        super(x, y, null);
        setDimension(48, 48);
    }
}
