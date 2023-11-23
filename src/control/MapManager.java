package control;

import model.GameObject;
import model.Map;
import model.boost.Boost;
import model.boost.BoostType;
import model.brick.Brick;
import model.brick.CoinBlue;
import model.brick.SurpriseBrick;
import model.enemy.Enemy;
import model.enemy.Goomba;
import model.enemy.Koopa;
import model.hero.Fireball;
import model.hero.Mario;

import java.awt.*;
import java.util.ArrayList;

/**
 * Creates and handles all the game-level collisions and
 * behaviours. It provides checks and low-level conditions
 * for working in sync with the {@link GameEngine}.
 *
 * @version 1.1.0
 */
public class MapManager {
    private Map map;
    private boolean isMultiplayer;

    ArrayList<GameObject> disposal = new ArrayList<>();

    public MapManager() {}

    /**
     * Creates the map using the {@link MapCreator}.
     *
     * @param mapName       The name of the map to be loaded.
     * @param isMultiplayer Whether the map needs two marios.
     * @return If the map was created successfully.
     */
    public boolean createMap(String mapName, boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;

        MapCreator mapCreator = new MapCreator();
        map = mapCreator.createMap(mapName, isMultiplayer);

        return map != null;
    }

    /**
     * Draws the currently loaded map, calling
     * {@link Map#drawMap(Graphics2D)}.
     *
     * @param g2D The graphics engine drawing the map.
     */
    public void drawMap(Graphics2D g2D) {
        if (map != null) map.drawMap(g2D);
    }

    /**
     * The master checker. It provides high-level conditions
     * to then call lower-level condition checkers for specific
     * collision contacts.
     *
     * @param engine The {@link GameEngine} to set the {@link GameStatus}.
     * @see #checkBlockCollisions(GameObject)
     * @see #checkBoostCollision(Boost)
     * @see #checkEnemyCollision(GameEngine)
     */
    public void checkCollisions(GameEngine engine) {
        Mario mario = map.getMario();

        // Mario checks
        checkBlockCollisions(mario);
        checkEnemyCollision(engine);

        if (mario.getY() > (48 * 16)) {
            if (map.getLives() == 0) engine.setGameStatus(GameStatus.GAME_OVER);
            else {
                int lives = map.getLives();
                GameEngine.playSound("death");
                engine.reset(isMultiplayer);
                map.setLives(lives - 1);
            }
        }

        // Boost checks
        for (Boost boost : map.getBoosts()) checkBoostCollision(boost);
        // Enemy checks
        for (Enemy enemy : map.getEnemies()) checkBlockCollisions(enemy);
        // Fireball checks
        for (Fireball fireball : map.getFireballs()) checkBlockCollisions(fireball);

        // Clear the items to remove
        for (GameObject object : disposal) {
            if (object instanceof Boost) map.getBoosts().remove((Boost) object);
            if (object instanceof CoinBlue) map.getBricks().remove((CoinBlue) object);
            if (object instanceof Enemy) map.getEnemies().remove((Enemy) object);
            if (object instanceof Fireball) map.getFireballs().remove((Fireball) object);
        }
    }

    /**
     * Lower-level collision checker for block collisions.
     *
     * @param toCheck The {@link GameObject} which
     *                collisions need to be checked.
     * @see #checkCollisions(GameEngine)
     */
    private void checkBlockCollisions(GameObject toCheck) {
        boolean air = true;

        for (Brick brick : map.getBricks()) {
            // -------------------- Checks top and bottom collisions --------------------
            if (toCheck.getVerticalBounds().intersects(brick.getBounds())) {
                // If Mario intersects with a coin
                if (brick instanceof CoinBlue && toCheck instanceof Mario) {
                    disposal.add(brick);
                    map.setCoins(map.getCoins() + 1);
                    GameEngine.playSound("coin");
                } // Stop the entity falling, if it's not a fireball
                else if (toCheck.getVelY() < 0 && !(toCheck instanceof Fireball)) {
                    toCheck.setVelY(0);
                    toCheck.setFalling(false);
                } // If it's a fireball, make it bounce off
                else if (toCheck instanceof Fireball) {
                    toCheck.setVelY(3);
                } // If the entity is jumping
                else if (toCheck.isJumping()) {
                    // If the entity is Mario and intersects a surprise brick on top
                    if (toCheck instanceof Mario && brick instanceof SurpriseBrick) {
                        int surpriseBrickIndex = map.getBrickIndex((int) brick.getX(), (int) brick.getY());
                        // If there are boosts left in the block, spawn them
                        if (((SurpriseBrick) map.getBricks().get(surpriseBrickIndex)).getBoostsAmount() > 0) {
                            Boost boost = new Boost(brick.getX(), brick.getY() - 48, MapCreator.coin);
                            boost.setType(surpriseBrickIndex, (Mario) toCheck);
                            map.getBoosts().add(boost);

                            if (boost.getBoostType() == BoostType.COIN) {
                                boost.setVelY(7);
                                map.setCoins(map.getCoins() + 1);
                                GameEngine.playSound("coin");
                            }

                            ((SurpriseBrick) map.getBricks().get(surpriseBrickIndex)).decrementBoosts();
                            if (((SurpriseBrick) map.getBricks().get(surpriseBrickIndex)).getBoostsAmount() == 0)
                                map.getBricks().get(surpriseBrickIndex).setStyle(MapCreator.surpriseBrickEmpty);
                        }
                    }

                    // And intersects a block on top, make it fall
                    toCheck.setVelY(0);
                    toCheck.setFalling(true);
                }

                // Unstuck the entity from the intersecting block
                if (toCheck.getY() + 48 > brick.getY() && !toCheck.isJumping()) toCheck.setY(brick.getY() - 48);
                air = false;
            }

            // -------------------- Checks right and left collisions --------------------
            if (toCheck.getHorizontalBounds().intersects(brick.getHorizontalBounds())) {
                // Check right collisions
                if (toCheck.getVelX() > 0) {
                    // If Mario intersects with a coin
                    if (brick instanceof CoinBlue && toCheck instanceof Mario) {
                        disposal.add(brick);
                        map.setCoins(map.getCoins() + 1);
                        GameEngine.playSound("coin");
                    }
                    // If Mario intersects a brick
                    else if (toCheck instanceof Mario) {
                        toCheck.setVelX(0);
                    } // If an enemy or a boost intersects with a block, make it bounce off
                    else if (toCheck instanceof Enemy || toCheck instanceof Boost) toCheck.setVelX(-toCheck.getVelX());
                }
                // Check left collisions
                else if (toCheck.getVelX() < 0) {
                    // If Mario intersects with a coin
                    if (brick instanceof CoinBlue && toCheck instanceof Mario) {
                        disposal.add(brick);
                        map.setCoins(map.getCoins() + 1);
                        GameEngine.playSound("coin");
                    }
                    // If Mario intersects a brick
                    else if (toCheck instanceof Mario) {
                        toCheck.setVelX(0);
                    } // If an enemy or a boost intersects with a block, make it bounce off
                    else if (toCheck instanceof Enemy || toCheck instanceof Boost) toCheck.setVelX(-toCheck.getVelX());
                }

                // If a fireball intersect a brick horizontally, simply remove it
                if (toCheck instanceof Fireball) disposal.add(toCheck);
            }

            // -------------------- Checks out of the map collisions --------------------
            if (!(toCheck instanceof Mario) && toCheck.getY() > (48 * 16)) disposal.add(toCheck);
        }

        // If an entity has air underneath, make it fall
        if (air) toCheck.setFalling(true);
    }

    /**
     * Lower-level collision checker for boost collisions.
     *
     * @param toCheck The {@link Boost} which
     *                collisions need to be checked.
     * @see #checkCollisions(GameEngine)
     */
    private void checkBoostCollision(Boost toCheck) {
        Mario mario = map.getMario();

        // If Mario intersects a boost
        if (toCheck.getBounds().intersects(mario.getBounds())) {
            if (toCheck.getBoostType() == BoostType.FIRE_FLOWER) {
                if (mario.isFire()) map.setPoints(map.getPoints() + 1000);
                else if (mario.isBabyStar() || mario.isStar()) mario.setFire(true);
                else {
                    if (!mario.isSuper()) mario.setY(mario.getY() - 48);
                    mario.setMarioSuper();
                    mario.setMarioFire();
                }
            }
            if (toCheck.getBoostType() == BoostType.HEART_MUSHROOM) {
                map.setLives((map.getLives() + 1));
                GameEngine.playSound("one-up");
            }
            if (toCheck.getBoostType() == BoostType.STAR) mario.setMarioStar();
            if (toCheck.getBoostType() == BoostType.SUPER_MUSHROOM) {
                mario.setY(mario.getY() - 48);
                mario.setMarioSuper();
                GameEngine.playSound("mushroom");
            }

            disposal.add(toCheck);
        }

        // Also check for normal collisions
        checkBlockCollisions(toCheck);
    }

    /**
     * Lower-level collision checker for enemy collisions.
     *
     * @param engine The {@link GameEngine} to set the {@link GameStatus}.
     * @see #checkCollisions(GameEngine)
     */
    private void checkEnemyCollision(GameEngine engine) {
        Mario mario = map.getMario();

        for (Enemy enemy : map.getEnemies()) {
            // If Mario is star and intersects an enemy, remove the enemy
            if ((mario.isBabyStar() || mario.isStar()) && mario.getBounds().intersects(enemy.getBounds())) {
                disposal.add(enemy);
                if (enemy instanceof Goomba) map.setPoints(map.getPoints() + 100);
                if (enemy instanceof Koopa) map.setPoints(map.getPoints() + 200);
            }
            // If Mario falls on an enemy, remove the enemy
            else if (mario.getVerticalBounds().intersects(enemy.getVerticalBounds()) && mario.getVelY() < 0) {
                disposal.add(enemy);
                if (enemy instanceof Goomba) map.setPoints(map.getPoints() + 100);
                if (enemy instanceof Koopa) map.setPoints(map.getPoints() + 200);
                mario.setVelY(4); // And make mario bounce
            }
            // In every other case, Mario dies or gets downgraded. Unless he's invincible
            else if (mario.getVerticalBounds().intersects(enemy.getVerticalBounds()) && !mario.isInvincible()) {
                if (mario.isSuper()) {
                    mario.setMarioSmall();
                    mario.setY(mario.getY() + 48);
                    mario.setInvincible(true);
                } else if (mario.isFire()) {
                    mario.setMarioSuper();
                    mario.setInvincible(true);
                } else {
                    if (map.getLives() == 0) engine.setGameStatus(GameStatus.GAME_OVER);
                    else {
                        int lives = map.getLives();
                        GameEngine.playSound("death");
                        engine.reset(isMultiplayer);
                        map.setLives(lives - 1);
                    }
                }
            }

            for (Fireball fireball : map.getFireballs()) {
                if (fireball.getHorizontalBounds().intersects(enemy.getHorizontalBounds())) {
                    disposal.add(enemy);
                    if (enemy instanceof Goomba) map.setPoints(map.getPoints() + 100);
                    if (enemy instanceof Koopa) map.setPoints(map.getPoints() + 200);
                    disposal.add(fireball);
                }
            }
        }
    }

    /**
     * Updates all entity/tiles locations
     * with {@link Map#updateLocations()}.
     */
    public void updateLocations() {
        if (map != null) map.updateLocations();
    }

    /* ---------- Getters / Setters ---------- */

    public Map getMap() {
        return map;
    }

    public boolean isMultiplayer() {
        return isMultiplayer;
    }
}
