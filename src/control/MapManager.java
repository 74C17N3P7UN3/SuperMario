package control;

import model.*;
import model.brick.*;
import model.enemy.Enemy;
import model.hero.Fireball;
import model.hero.Mario;
import model.boost.Boost;
import model.boost.BoostType;

import java.awt.*;
import java.util.ArrayList;

// FIXME: Calza Reminder
/**
 * Creates and handles all the game-level collisions and
 * behaviours. It provides checks and low-level conditions
 * for working in sync with the {@link GameEngine}.
 *
 * @version 0.1.0
 */
public class MapManager {
    private Map map;
    private MapCreator mapCreator;
    private GameEngine engine;
    ArrayList<GameObject> disposal = new ArrayList<>();

    public MapManager() {}

    /**
     * Creates the map using the {@link MapCreator}.
     *
     * @param mapName     The name of the map to be loaded.
     * @return If the map was created successfully.
     */
    public boolean createMap(String mapName, GameEngine engine) {
        mapCreator = new MapCreator();
        map = mapCreator.createMap(mapName);
        this.engine = engine;

        return map != null;
    }

    /**
     * Draws the currently loaded map, calling
     * {@link Map#drawMap(Graphics2D)}.
     *
     * @param g2D The Graphics engine to draw the map.
     */
    public void drawMap(Graphics2D g2D) {
        if (map != null) map.drawMap(g2D);
    }

    /**
     * Resets the current map.
     *
     * @param engine The {@link GameEngine} object.
     */
    public void resetMap(GameEngine engine) {
        getMario().resetLocation();
        engine.resetCamera();

        createMap(map.getName(),engine);
    }

    public void checkCollisions(GameEngine gameEngine) {
        Mario mario = getMario();

        checkBlockCollisions(mario);
        checkEnemyCollision(mario, gameEngine);
        if(mario.getY() >= (48 * 14)) {
        	if(gameEngine.getLives() == 0)gameEngine.setGameStatus(GameStatus.GAME_OVER);
        	else {
        		gameEngine.setLives((gameEngine.getLives() - 1));
                GameEngine.playSound("death");
        		gameEngine.reset();
        	}
        }

        for(Enemy enemy : map.getEnemies()) {
            checkBlockCollisions(enemy);
        }

        for(Fireball fireball : map.getFireballs()) {
        	checkBlockCollisions(fireball);
        }

        //BOOSTS

        for(Boost boost : mapCreator.getBoosts()){
            if(boost.getBounds().intersects(mario.getBounds())) {
                if(boost.getType() == BoostType.SUPER_MUSHROOM){
                    mario.setY(mario.getY()-48);
                    mario.setMarioSuper();
                    GameEngine.playSound("super-mario");
                }
                if(boost.getType() == BoostType.STAR){
                    mario.setMarioStar();
                }
                if(boost.getType() == BoostType.FIRE_FLOWER){
                    if(mario.isStar() || mario.isBabyStar()) mario.setIsFire(true);
                    else {
                        if(!mario.isSuper()) {
                        	mario.setY(mario.getY()-48);
                        }
                    	mario.setMarioSuper();
                        mario.setMarioFire();

                    }
                }
                if(boost.getType() == BoostType.HEART_MUSHROOM) {
                	gameEngine.setLives((gameEngine.getLives() + 1));
                    GameEngine.playSound("one-up");
                }
                disposal.add(boost);
            }

            checkBlockCollisions(boost);

        }

        for(GameObject object : disposal) {
            if (object instanceof Boost) map.getBoosts().remove((Boost) object);
            if (object instanceof Enemy) map.getEnemies().remove((Enemy) object);
            if (object instanceof Fireball) map.getFireballs().remove((Fireball) object);
            if (object instanceof CoinBlue) map.getBricks().remove((CoinBlue)object);
        }

    }

    private void checkBlockCollisions(GameObject toCheck){
        boolean air = true;
        for(Brick block : map.getBricks()){
            //checks bottom and upper collision
            if(toCheck.getVerticalBounds().intersects(block.getBounds())){
            	if(block instanceof CoinBlue && toCheck instanceof Mario) {
            		disposal.add(block);
            		engine.setCoins(engine.getCoins()+1);
                    GameEngine.playSound("coin");
            	}
            	else if(toCheck.getVelY() < 0 && !(toCheck instanceof Fireball)){
                    toCheck.setVelY(0);
                    toCheck.setFalling(false);
                }else if(toCheck instanceof Fireball) {
                	toCheck.setVelY(3);
                }else if(toCheck.isJumping()){
                    if(block instanceof SurpriseBrick){
                    	if(toCheck instanceof Mario) {
                    		int n = map.getBlockPosition((int)block.getX(),(int)block.getY());
                            if(((SurpriseBrick) map.getBricks().get(n)).isBoost()){
                                Boost boost = new Boost(block.getX(), block.getY()-48, MapCreator.coin);

                                boost.setType(n, (Mario) toCheck);

                                map.addBoost(boost);
                                if(boost.getType() == BoostType.COIN) {
                                	boost.setVelY(7);
                                	engine.setCoins(engine.getCoins()+1);
                                    GameEngine.playSound("coin");
                                }

                                ((SurpriseBrick) map.getBricks().get(n)).setBoost(false);
                                map.getBricks().get(n).setStyle(MapCreator.emptySurpriseBrick);
                            }
                        }
                    }
                    toCheck.setVelY(0);
                    toCheck.setFalling(true);
                }

                if(toCheck.getY()+48 > block.getY() && !toCheck.isJumping()){
                    toCheck.setY(block.getY()-48);
                }
                air = false;
            }

            //checks right and left collision
            if(toCheck.getHorizontalBounds().intersects(block.getHorizontalBounds())){
                if(toCheck.getVelX() > 0){
                	if(block instanceof CoinBlue && toCheck instanceof Mario) {
                		disposal.add(block);
                		engine.setCoins(engine.getCoins()+1);
                        GameEngine.playSound("coin");
                	}
                	else if(toCheck instanceof Mario)
                        toCheck.setVelX(0);
                    else if(toCheck instanceof Enemy || toCheck instanceof Boost)
                        toCheck.setVelX(-toCheck.getVelX());

                }else if(toCheck.getVelX() < 0){
                	if(block instanceof CoinBlue && toCheck instanceof Mario) {
                		disposal.add(block);
                		engine.setCoins(engine.getCoins()+1);
                        GameEngine.playSound("coin");
                	}
                	else if(toCheck instanceof Mario)
                        toCheck.setVelX(0);
                    else if(toCheck instanceof Enemy || toCheck instanceof Boost)
                        toCheck.setVelX(-toCheck.getVelX());
                }
                if(toCheck instanceof Fireball)  disposal.add(toCheck);
            }

            if(toCheck instanceof Fireball && toCheck.getY() >= 48*14) disposal.add(toCheck);

        }
        if(air){
            toCheck.setFalling(true);
        }

    }

    public void checkEnemyCollision(Mario mario, GameEngine engine){
        for(Enemy enemy : map.getEnemies()){
            if((mario.isStar() || mario.isBabyStar()) && mario.getBounds().intersects(enemy.getBounds())) disposal.add(enemy);
            else if(mario.getVerticalBounds().intersects(enemy.getVerticalBounds()) && mario.getVelY() < 0){
                disposal.add(enemy);
                mario.setVelY(4);
            }else if(mario.getVerticalBounds().intersects(enemy.getVerticalBounds()) && !mario.isInvincible()){
                if(mario.isSuper()){
                	mario.setMarioMini();
                    mario.setY(mario.getY()+48);
                    mario.setInvincible(true);
                }else if(mario.isFire()){
                    mario.setMarioSuper();
                    mario.setInvincible(true);
                }else {
                	if(engine.getLives() == 0)engine.setGameStatus(GameStatus.GAME_OVER);
                	else {
                		engine.setLives((engine.getLives() - 1));
                        GameEngine.playSound("death");
                		engine.reset();
                	}
                }
            }

            for (Fireball fireball : map.getFireballs()) {
            	if(fireball.getHorizontalBounds().intersects(enemy.getHorizontalBounds())) {
            		disposal.add(enemy);
            		disposal.add(fireball);
            	}
            }
        }
    }

    /**
     * Updates all entity/tiles locations
     * with {@link Map#updateLocations}.
     */
    public void updateLocations() {
        if (map != null) map.updateLocations();
    }

    public void addFireball(Fireball fireball) {
    	map.addFireBall(fireball);
    }

    /* ---------- Getters / Setters ---------- */

    public EndFlag getEndPoint() {
        return map.getEndPoint();
    }

    public Map getMap() {
        return map;
    }

    public Mario getMario() {
        return map.getMario();
    }
}
