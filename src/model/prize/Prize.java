package model.prize;

import control.GameEngine;
import model.hero.Mario;

import java.awt.*;

public interface Prize {
    Rectangle getBounds();

    int getPoint();

    void onTouch(Mario mario, GameEngine engine);

    void reveal();
}
