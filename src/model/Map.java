package model;

import model.brick.Brick;
import model.enemy.Enemy;
import model.hero.Mario;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {
    private BufferedImage backgroundImage;

    private Mario mario;

    private ArrayList<Brick> bricks = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public Map(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void addBrick(Brick brick) {
        this.bricks.add(brick);
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public void drawMap(Graphics2D g2D) {
        drawBackground(g2D);
        drawBricks(g2D);
        drawEnemies(g2D);
        drawMario(g2D);
    }

    public void drawBackground(Graphics2D g2D) {
        g2D.drawImage(backgroundImage, 0, 0, null);
    }

    private void drawBricks(Graphics2D g2D) {
        for (Brick brick : bricks)
            g2D.drawImage(brick.getStyle(), (int) brick.getX(), (int) brick.getY(), null);
    }

    private void drawEnemies(Graphics2D g2D) {
        for (Enemy enemy : enemies)
            g2D.drawImage(enemy.getStyle(), (int) enemy.getX(), (int) enemy.getY(), null);
    }

    private void drawMario(Graphics2D g2D) {
        mario.drawObject(g2D);
    }

    public void updateLocations() {
        // Updates Mario's location
        mario.updateLocation();

        // Updates enemies' locations
        for (Enemy enemy : enemies) enemy.updateLocation();
    }

    /* ---------- Getters / Setters ---------- */

    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }
}
