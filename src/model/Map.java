package model;

import model.brick.Brick;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {
    private BufferedImage backgroundImage;
    private ArrayList<Brick> bricks = new ArrayList<>();

    public Map(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void addBrick(Brick brick) {
        this.bricks.add(brick);
    }

    public void drawMap(Graphics2D g2D) {
        drawBackground(g2D);
        drawBricks(g2D);
    }

    // TODO: Javadoc
    public void drawBackground(Graphics2D g2D) {
        g2D.drawImage(backgroundImage, 0, 0, null);
    }

    // TODO: Javadoc
    private void drawBricks(Graphics2D g2D) {
        for(Brick brick : bricks)
        	g2D.drawImage(brick.getStyle(), (int)brick.getX(), (int)brick.getY(), null);
    }
    
}
