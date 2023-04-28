package control;

import model.Map;
import model.enemy.Enemy;
import model.hero.Mario;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

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
        int surpriseBrickRGB = new Color(163, 73, 164).getRGB();
        int pipeHeadRGB = new Color(34, 177, 76).getRGB();
        int groundBrickRGB = new Color(237, 28, 36).getRGB();*/
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

        // Checks the block above Mario
        // FIXME: Bottom code needs to be revised
        blockToCheck = new Point((int) marioBlockPosition.getX(), (int) marioBlockPosition.getY() - 1);
        colorToCheck = mapCreator.getMapImage().getRGB(blockToCheck.x,blockToCheck.y);

        if (colorToCheck != air && colorToCheck != marioRGB) {
            if (colorToCheck == goombaRGB || colorToCheck == koopaRGB) {
                // TODO: FINISH HIM
            } else {
                // System.out.println(colorToCheck);
                int startingX = (int) blockToCheck.getX() * 48;
                int startingY = (int) blockToCheck.getY() * 48;
                Rectangle above = new Rectangle(startingX, startingY, 48, 48);

                if (above.intersects(mario.getTopBounds())) {
                    if(mario.isJumping()) mario.setVelY(0);
                	mario.setJumping(false);
                    mario.setFalling(true);
                    
                }
            }
        }

        // Checks the block to the right of Mario
        blockToCheck = new Point((int) marioBlockPosition.getX() + 1, (int) marioBlockPosition.getY());
        colorToCheck = mapImage.getRGB((int) blockToCheck.getX(), (int) blockToCheck.getY());
        if (colorToCheck != air) {
            if (colorToCheck == goombaRGB || colorToCheck == koopaRGB) {
                // TODO: FINISH HIM
            } else {
                int startingX = (int) blockToCheck.getX() * 48;
                int startingY = (int) blockToCheck.getY() * 48;
                Rectangle right = new Rectangle(startingX, startingY, 48, 48);

                if (right.intersects(mario.getRightBounds()) && mario.getVelX() > 0) {
                	mario.setVelX(0);
                	System.out.println("Ciao");
                }
            }
        }

        // Checks the block below Mario
        blockToCheck = new Point((int) marioBlockPosition.getX(), (int) marioBlockPosition.getY() + 1);
        colorToCheck = mapImage.getRGB((int) blockToCheck.getX(), (int) blockToCheck.getY());
        
            if (colorToCheck == goombaRGB || colorToCheck == koopaRGB) {
                // TODO: FINISH HIM
            } else if(colorToCheck != marioRGB){
                int startingX = (int) blockToCheck.getX() * 48;
                int startingY = (int) blockToCheck.getY() * 48;
                Rectangle under = new Rectangle(startingX, startingY, 48, 48);

                if (!mario.isFalling() && !checkCollisionRGB(colorToCheck)) {
                	System.out.println("Hello");
                    mario.setFalling(true);
                }else if(under.intersects(mario.getBottomBounds()) && checkCollisionRGB(colorToCheck)){
                	if(mario.isFalling()) mario.setVelY(0);
                	mario.setFalling(false);
                }
            }


        // Checks the block to the left of Mario
        blockToCheck = new Point((int) marioBlockPosition.getX() - 1, (int) marioBlockPosition.getY());
        colorToCheck = mapImage.getRGB((int) blockToCheck.getX(), (int) blockToCheck.getY());
        if (colorToCheck != air) {
            if (colorToCheck == goombaRGB || colorToCheck == koopaRGB) {
                // TODO: FINISH HIM
            } else {
                int startingX = (int) blockToCheck.getX() * 48;
                int startingY = (int) blockToCheck.getY() * 48;
                Rectangle right = new Rectangle(startingX, startingY, 48, 48);

                if (right.intersects(mario.getLeftBounds()) && mario.getVelX() < 0) {
                	mario.setVelX(0);
                	
                }
            }
        }

        //TODO: WE NEED TO TRANSFORM THIS IN METHODS BECAUSE IS SPAGHETTI

        for(Enemy enemy : mapCreator.getEnemies()){
            Point enemyPos = new Point(((int)enemy.getX()+24) /48, (int) (enemy.getY()+24) / 48);


            //Checks if the enemy has a block under them
            blockToCheck = new Point(enemyPos.x,enemyPos.y+1);
            colorToCheck = mapImage.getRGB(blockToCheck.x,blockToCheck.y);
            if(colorToCheck != goombaRGB && colorToCheck != koopaRGB){

                if(checkCollisionRGB(colorToCheck)){
                    Rectangle under = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,48,48);
                    if(enemy.getBottomBounds().intersects(under)){
                        enemy.setFalling(false);
                        enemy.setJumping(false);
                        enemy.setVelY(0);
                    }
                }else if(colorToCheck == dead) {
                    enemy.setFalling(false);
                    	//System.out.println("dead");
                }else if(colorToCheck == air){
                    enemy.setFalling(true);
                    	//System.out.println(enemy.getVelY());
                    }
            }

            //Checks if the enemy has a block above them
            blockToCheck = new Point(enemyPos.x,enemyPos.y+1);
            colorToCheck = mapImage.getRGB(blockToCheck.x,blockToCheck.y);
            Rectangle above = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,48,48);
            if(colorToCheck != air && colorToCheck != goombaRGB && colorToCheck != koopaRGB){
                if(enemy.getTopBounds().intersects(above)){
                    enemy.setFalling(true);
                    enemy.setJumping(false);
                    enemy.setVelY(0);
                }
            }

            //Checks if the enemy has a block at the right
            blockToCheck = new Point(enemyPos.x+1,enemyPos.y);
            colorToCheck = mapImage.getRGB(blockToCheck.x,blockToCheck.y);
            Rectangle right = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,48,48);
            if(colorToCheck != air && colorToCheck != goombaRGB && colorToCheck != koopaRGB){
                if(enemy.getRightBounds().intersects(right)){
                    enemy.setVelX(-enemy.getVelX());
                }
            }

            //Checks if the enemy has a block at the left
            blockToCheck = new Point(enemyPos.x-2,enemyPos.y);
            colorToCheck = mapImage.getRGB(blockToCheck.x,blockToCheck.y);
            
            if(blockToCheck.x == 0) enemy.setVelX(-enemy.getVelX());
            
            if(colorToCheck != air && colorToCheck != goombaRGB && colorToCheck != koopaRGB){
            	if(colorToCheck == pipeBodyRGB){
            		
            		Rectangle left = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,96,48);
            		if(enemy.getLeftBounds().intersects(left)) enemy.setVelX(-enemy.getVelX());
            	
            	}else {
            		blockToCheck = new Point(enemyPos.x-2,enemyPos.y);
            		Rectangle left = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,60,48);
            		
                    if(enemy.getLeftBounds().intersects(left)){
                        enemy.setVelX(-enemy.getVelX());
                    }
                }
            }
        }


    }

    private boolean checkCollisionRGB(int colorToCheck){
        int blockRGB = new Color(127, 127, 127).getRGB();
        int groundBrickRGB = new Color(237, 28, 36).getRGB();
        int ordinaryBrickRGB = new Color(185, 122, 87).getRGB();
        int surpriseBrickRGB = new Color(163, 73, 164).getRGB();
        int pipeBodyRGB = new Color(181, 230, 29).getRGB();
        int pipeHeadRGB = new Color(34, 177, 76).getRGB();
        if(colorToCheck == blockRGB || colorToCheck == groundBrickRGB || colorToCheck == ordinaryBrickRGB || colorToCheck == surpriseBrickRGB || colorToCheck ==  pipeHeadRGB || colorToCheck == pipeBodyRGB) return true;
        else return false;
    }

    /**
     * Updates all entity/tiles locations
     * with {@link Map#updateLocations()}.
     */
    public void updateLocations() {
        if (map != null) map.updateLocations();
    }

    /* ---------- Getters / Setters ---------- */

    public Mario getMario() {
        return map.getMario();
    }
}
