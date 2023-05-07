package model;

import model.boost.Boost;
import model.boost.BoostType;
import model.brick.Brick;
import model.brick.SurpriseBrick;
import model.enemy.Enemy;
import model.hero.Fireball;
import model.hero.Mario;
import utils.ImageImporter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {
    private BufferedImage backgroundImage;
    private String name;

    private Mario mario;
    private EndFlag endPoint;

    private ArrayList<Boost> boosts = new ArrayList<>();
    private ArrayList<Brick> bricks = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Fireball> fireballs = new ArrayList<>();

    public Map(String mapName) {
        backgroundImage = ImageImporter.loadImage("background");
        name = mapName;
    }

    public void addBoost(Boost boost) {
        boosts.add(boost);
    }

    public void addBrick(Brick brick) {
        bricks.add(brick);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addFireBall(Fireball fireball) {
        fireballs.add(fireball);
    }

    public void drawMap(Graphics2D g2D) {
        drawBackground(g2D);

        mario.drawObject(g2D);
        endPoint.drawObject(g2D);

        drawBoosts(g2D);
        drawBricks(g2D);
        drawEnemies(g2D);
        drawFireballs(g2D);
    }

    public void drawBackground(Graphics2D g2D) {
        g2D.drawImage(backgroundImage, 0, 0, null);
    }

    private void drawBoosts(Graphics2D g2D) {
        for (int i = boosts.size() - 1; i >= 0; i--) {
            Boost boost = boosts.get(i);
            if (boost.getType() == BoostType.COIN && boost.getVelY() == 0)
                boosts.remove(boost);
            else boost.drawObject(g2D);
        }
    }

    private void drawBricks(Graphics2D g2D) {
        for (Brick brick : bricks) g2D.drawImage(brick.getStyle(), (int) brick.getX(), (int) brick.getY(), null);
    }

    private void drawEnemies(Graphics2D g2D) {
        for (Enemy enemy : enemies) enemy.drawObject(g2D);
    }

    private void drawFireballs(Graphics2D g2D) {
        for (Fireball fireball : fireballs) fireball.drawObject(g2D);
    }

    public void updateLocations() {
        // Updates Mario's location
        mario.updateLocation();

        // Updates enemies' locations
        for (Enemy enemy : enemies) enemy.updateLocation();
        for (Boost boost : boosts) boost.updateLocation();
        for (Fireball fireball : fireballs) fireball.updateLocation();

        // Updates flag's location
        endPoint.updateLocation();
    }

    public int getBlockPosition(int x, int y) {
        int pos = 0;
        for (Brick brick : bricks) {
            if (brick instanceof SurpriseBrick)
                if (brick.getX() == x && brick.getY() == y)
                    return pos;

            pos++;
        }

        return -1;
    }

    /* ---------- Getters / Setters ---------- */

    public ArrayList<Boost> getBoosts() {
        return boosts;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public EndFlag getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(EndFlag endPoint) {
        this.endPoint = endPoint;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<Fireball> getFireballs() {
        return fireballs;
    }

    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }

    public String getName() {
        return name;
    }
}
