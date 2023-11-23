package model;

import model.boost.Boost;
import model.boost.BoostType;
import model.brick.Brick;
import model.enemy.Enemy;
import model.hero.Fireball;
import model.hero.Mario;
import utils.ImageImporter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The Map that gets rendered and contains all information
 * about what objects are in it, the current player's progress.
 *
 * @version 1.1.0
 */
public class Map {
    private final BufferedImage backgroundImage;

    private int coins, lives, points, time;

    private Mario mario;
    private Mario netMario;
    private EndFlag endPoint;

    private final ArrayList<Boost> boosts = new ArrayList<>();
    private final ArrayList<Brick> bricks = new ArrayList<>();
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Fireball> fireballs = new ArrayList<>();

    public Map() {
        backgroundImage = ImageImporter.loadImage("background");

        coins = 0;
        lives = 3;
        points = 0;
        time = 120;
    }

    /**
     * Draws all the map's components.
     *
     * @param g2D The graphics engine.
     */
    public void drawMap(Graphics2D g2D) {
        drawBackground(g2D);

        mario.drawObject(g2D);
        if (netMario != null) netMario.drawObject(g2D);
        endPoint.drawObject(g2D);

        drawBoosts(g2D);
        drawBricks(g2D);
        drawEnemies(g2D);
        drawFireballs(g2D);
    }

    /**
     * Draws the map's background.
     *
     * @param g2D The graphics engine.
     * @see #drawMap(Graphics2D)
     */
    public void drawBackground(Graphics2D g2D) {
        g2D.drawImage(backgroundImage, 0, 0, null);
    }

    /**
     * Draws the map's boosts.
     *
     * @param g2D The graphics engine.
     * @see #drawMap(Graphics2D)
     */
    private void drawBoosts(Graphics2D g2D) {
        for (int i = 0; i < boosts.size(); i++) {
            Boost boost = boosts.get(i);
            if (boost.getBoostType() == BoostType.COIN && boost.getVelY() == 0) boosts.remove(boost);
            else boost.drawObject(g2D);
        }
    }

    /**
     * Draws the map's bricks.
     *
     * @param g2D The graphics engine.
     * @see #drawMap(Graphics2D)
     */
    private void drawBricks(Graphics2D g2D) {
        for (Brick brick : bricks) g2D.drawImage(brick.getStyle(), (int) brick.getX(), (int) brick.getY(), null);
    }

    /**
     * Draws the map's enemies.
     *
     * @param g2D The graphics engine.
     * @see #drawMap(Graphics2D)
     */
    private void drawEnemies(Graphics2D g2D) {
        for (Enemy enemy : enemies) enemy.drawObject(g2D);
    }

    /**
     * Draws the map's fireballs.
     *
     * @param g2D The graphics engine.
     * @see #drawMap(Graphics2D)
     */
    private void drawFireballs(Graphics2D g2D) {
        for (Fireball fireball : fireballs) fireball.drawObject(g2D);
    }

    /**
     * Returns the index in the brick's array of the given
     * surprise brick at the {@code (x, y)} coordinates.
     *
     * @param x The x coordinate of the surprise brick.
     * @param y The y coordinate of the surprise brick.
     * @return The index pf the surprise brick in the array.
     */
    public int getBrickIndex(int x, int y) {
        for (int i = 0; i < bricks.size(); i++)
            if (bricks.get(i).getX() == x && bricks.get(i).getY() == y) return i;
        return -1;
    }

    /**
     * Updates all entities locations every game tick.
     */
    public void updateLocations() {
        mario.updateLocation();
        if (netMario != null) netMario.updateLocation();

        for (Boost boost : boosts) boost.updateLocation();
        for (Enemy enemy : enemies) enemy.updateLocation();
        for (Fireball fireball : fireballs) fireball.updateLocation();

        endPoint.updateLocation();
    }

    /* ---------- Getters / Setters ---------- */

    public ArrayList<Boost> getBoosts() {
        return boosts;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
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

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Mario getMario() {
        return mario;
    }

    public void setMario(Mario mario) {
        this.mario = mario;
    }

    public Mario getNetMario() {
        return netMario;
    }

    public void setNetMario(Mario netMario) {
        this.netMario = netMario;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
