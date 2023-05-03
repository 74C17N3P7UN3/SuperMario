package control;

import model.*;
import model.brick.*;
import model.enemy.Enemy;
import model.hero.Fireball;
import model.hero.Mario;
import model.hero.MarioForm;
import model.prize.Boost;
import model.prize.BoostType;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Creates and handles all the game-level collisions and
 * behaviours. It provides checks and low-level conditions
 * for working in sync with the {@link GameEngine}.
 *
 * @author TacitNeptune
 * @version 0.1.0
 */
public class MapManager {
    private Map map;
    private MapCreator mapCreator;

    public MapManager() {}

    /**
     * Creates the map using the {@link MapCreator}.
     *
     * @param imageLoader The loader responsible for
     *                    drawing the map in the creator.
     * @param mapName     The name of the map to be loaded.
     * @return If the map was created successfully.
     */
    public boolean createMap(ImageLoader imageLoader, String mapName) {
        mapCreator = new MapCreator(imageLoader);
        map = mapCreator.createMap(mapName);

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

        createMap(engine.getImageLoader(), map.getName());
    }

    public void checkCollisions() {
        BufferedImage mapImage = mapCreator.getMapImage();

        /*int blockRGB = new Color(127, 127, 127).getRGB();

        int ordinaryBrickRGB = new Color(185, 122, 87).getRGB();
        int groundBrickRGB = new Color(237, 28, 36).getRGB();*/
        int surpriseBrickRGB = new Color(163, 73, 164).getRGB();
        int pipeHeadRGB = new Color(34, 177, 76).getRGB();
        int pipeBodyRGB = new Color(181, 230, 29).getRGB();
        int dead = new Color (255, 253, 85).getRGB();
        int marioRGB = new Color(255, 127, 39).getRGB();
        int air = new Color(0, 0, 0).getRGB();

        int goombaRGB = new Color(63, 72, 204).getRGB();
        int koopaRGB = new Color(255, 174, 201).getRGB();

        Mario mario = getMario();
        Point marioBlockPosition = new Point((int) (mario.getX() + 24) / 48, (int) (mario.getY() + 24) / 48);

        Point blockToCheck;
        int colorToCheck;

        ArrayList<GameObject> disposal = new ArrayList<>();

        checkBlockCollisions(mario);
        checkEnemyCollision(mario);
        if(mario.getX() >= 48*198 - 20){
            map.getEndPoint().setTouched(true);
        }

        for(Enemy enemy : map.getEnemies()) {
            checkBlockCollisions(enemy);
        }
        
        for(Fireball fireball : map.getFireballs()) {
        	checkFireballCollisions(fireball);
        }

        //BOOSTS

        for(Boost boost : mapCreator.getBoosts()){
            if(boost.getBounds().intersects(mario.getBounds())) {
                if(boost.getType() == BoostType.superMushroom){
                    mario.setY(mario.getY()-48);
                    mario.setMarioBig();
                }
                if(boost.getType() == BoostType.starMan){
                    mario.setMarioStar();
                }
                if(boost.getType() == BoostType.fireFlower){
                    mario.setMarioFire();
                }
                if(boost.getType() == BoostType.mushroom1Up) {
                	//ciao;
                }	
                disposal.add(boost);
            }

            checkBlockCollisions(boost);

        }

        for(GameObject object : disposal) {
            if (object instanceof Boost) map.getBoosts().remove((Boost) object);
            if (object instanceof Enemy) map.getEnemies().remove((Enemy) object);
        }

    }

    private void checkBlockCollisions(GameObject toCheck){
        boolean air = true;
        for(Brick block : map.getBricks()){
            //checks bottom and upper collision
            if(toCheck.getVerticalBounds().intersects(block.getBounds())){
                if(toCheck.getVelY() < 0){
                    toCheck.setVelY(0);
                    toCheck.setFalling(false);
                }
                else if(toCheck.isJumping()){
                    if(block instanceof SurpriseBrick){

                        int n = map.getPositionBlock((int)block.getX(),(int)block.getY());
                        if(((SurpriseBrick) map.getBricks().get(n)).getBoost()){
                            Boost boost = new Boost(block.getX(), block.getY()-48, mapCreator.getVoidBoost());

                            boost.setType(mapCreator, n, (Mario)toCheck);

                            map.addBoost(boost);
                            if(boost.getType() == BoostType.money)boost.setVelY(7);

                            ((SurpriseBrick) map.getBricks().get(n)).setBoost(false);
                            map.getBricks().get(n).setStyle(mapCreator.getEmptySurpriseBrick());
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

                    if(toCheck instanceof Mario)
                        toCheck.setVelX(0);
                    else if(toCheck instanceof Enemy || toCheck instanceof Boost)
                        toCheck.setVelX(-toCheck.getVelX());

                }else if(toCheck.getVelX() < 0){

                    if(toCheck instanceof Mario)
                        toCheck.setVelX(0);
                    else if(toCheck instanceof Enemy || toCheck instanceof Boost)
                        toCheck.setVelX(-toCheck.getVelX());
                }
            }
        }
        if(air){
            toCheck.setFalling(true);
        }

    }

    public void checkEnemyCollision(Mario mario){
        ArrayList<Enemy> disposal = new ArrayList<Enemy>();
        for(Enemy enemy : map.getEnemies()){
            if((mario.isStar() || mario.isBabyStar()) && mario.getBounds().intersects(enemy.getBounds())) disposal.add(enemy);
            else if(mario.getVerticalBounds().intersects(enemy.getVerticalBounds()) && mario.getVelY() < 0){
                disposal.add(enemy);
            }else if(mario.getVerticalBounds().intersects(enemy.getVerticalBounds()) && !mario.isInvincible()){
                if(mario.isSuper()){
                    mario.setY(mario.getY()+48);
                    mario.setMarioMini();
                    mario.setInvincible(true);
                }else if(mario.isFire()){
                    mario.setMarioBig();
                    mario.setInvincible(true);
                }
            }
        }
        for(Enemy enemy : disposal){
            map.getEnemies().remove(enemy);
        }
    }
    
    //TODO: sistemare metodo per il movimento delle fireball
    public void checkFireballCollisions(GameObject toCheck) {
    	ArrayList<Fireball> disposal = new ArrayList<Fireball>();
    	for(Brick block : map.getBricks()){
    		//checks bottom and upper collision
            if(toCheck.getVerticalBounds().intersects(block.getBounds())){
            	toCheck.setVelY(3);
            }
    		
        	//checks right and left collision
            if(toCheck.getHorizontalBounds().intersects(block.getHorizontalBounds())){
            	disposal.add((Fireball) toCheck);
            }
    	}
    	for(Fireball fireball : disposal) {
    		map.getFireballs().remove(fireball);
    	}
    }

    /**
     * Updates all entity/tiles locations
     * with {@link Map#updateLocations()}.
     */
    public void updateLocations() {
        if (map != null) map.updateLocations();
    }
    
    public void addFireball(Fireball fireball) {
    	map.addFireBall(fireball);
    }
    
    /* ---------- Getters / Setters ---------- */

    public Mario getMario() {
        return map.getMario();
    }
    
    public BufferedImage getFireball() {
    	return mapCreator.getFireball();
    }
    
}
