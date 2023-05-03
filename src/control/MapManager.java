package control;

import model.*;
import model.brick.SurpriseBrick;
import model.enemy.Enemy;
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
    private Graphics2D g2D;

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
        this.g2D = g2D;
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

        // Checks the block above Mario
        // FIXME: Bottom code needs to be revised
        if(marioBlockPosition.getY() > 1) {
        	blockToCheck = new Point((int) marioBlockPosition.getX(), (int) marioBlockPosition.getY() - 1);
        	colorToCheck = mapCreator.getMapImage().getRGB(blockToCheck.x,blockToCheck.y);

            if (colorToCheck != air && colorToCheck != marioRGB) {
                // System.out.println(colorToCheck);
                int startingX = (int) blockToCheck.getX() * 48;
                int startingY = (int) blockToCheck.getY() * 48;
                Rectangle above = new Rectangle(startingX, startingY, 48, 48);

                if (colorToCheck == surpriseBrickRGB) {
                    if(above.intersects(mario.getTopBounds())) {
                        if(mario.isJumping()) mario.setVelY(0);
                        mario.setJumping(false);
                        mario.setFalling(true);

                        //find the surprise brick above mario
                        int n = map.getPositionBlock(startingX, startingY);

                        //the surprise brick has the boost
                        if(((SurpriseBrick) map.getBricks().get(n)).getBoost()) {
                            Boost boost = new Boost(startingX, startingY-48, mapCreator.getStar());
                            boost.setType(mapCreator, n, mario);
                            map.addBoost(boost);

                            ((SurpriseBrick) map.getBricks().get(n)).setBoost(false);
                            map.getBricks().get(n).setStyle(mapCreator.getEmptySurpriseBrick());
                        }
                    }
                }else if(checkCollisionRGB(colorToCheck)) {
                    if(above.intersects(mario.getTopBounds())) {
                        if(mario.isJumping()) mario.setVelY(0);
                        mario.setJumping(false);
                        mario.setFalling(true);
                    }
                }
            }
        }

        // Checks the block to the right of Mario
        blockToCheck = new Point((int) marioBlockPosition.getX() + 1, (int) marioBlockPosition.getY());
        colorToCheck = mapImage.getRGB((int) blockToCheck.getX(), (int) blockToCheck.getY());
        if(mario.getX() >= 48*198 - 20){
            map.getEndPoint().setTouched(true);
        }
        else if (colorToCheck != air && colorToCheck != koopaRGB && colorToCheck != goombaRGB) {
                int startingX = (int) blockToCheck.getX() * 48;
                int startingY = (int) blockToCheck.getY() * 48;
                Rectangle right = new Rectangle(startingX, startingY, 48, 48);

                if (right.intersects(mario.getRightBounds()) && mario.getVelX() > 0) {
                	mario.setVelX(0);
                	//System.out.println("Ciao");
                }
        }

        // Checks the block below Mario
        blockToCheck = new Point((int) marioBlockPosition.getX(), (int) marioBlockPosition.getY() + 1);
        colorToCheck = mapImage.getRGB((int) blockToCheck.getX(), (int) blockToCheck.getY());
        int colorToCheckPipe = mapImage.getRGB((int) blockToCheck.getX() -1, (int) blockToCheck.getY());

            if(colorToCheck != marioRGB){
                int startingX = (int) blockToCheck.getX() * 48;
                int startingY = (int) blockToCheck.getY() * 48 - 4;
                Rectangle under = new Rectangle(startingX, startingY, 48, 48);
                Rectangle underpipe = new Rectangle(startingX-1,startingY,48,48);
                if (!mario.isFalling() && !checkCollisionRGB(colorToCheck) && colorToCheckPipe != pipeHeadRGB) {
                	//System.out.println("Hello");
                    mario.setFalling(true);
                }else if(checkCollisionRGB(colorToCheck) || colorToCheckPipe == pipeHeadRGB){

                	if(under.intersects(mario.getBottomBounds())) {
                    	if(mario.isFalling()) {
                            mario.setVelY(0);
                            //FIXME:mario under hitboxed in superform isnt the right ones, need a fix
                            /*if(mario.getMarioForm().isSuper()){

                            }*/
                        }
                    	mario.setFalling(false);

                	}
                	else if(underpipe.intersects(mario.getBottomBounds())){
                		if(mario.isFalling()) mario.setVelY(0);
                    	mario.setFalling(false);
                	}
                }
            }


        // Checks the block to the left of Mario
        if(mario.getX() < 96) {
        	mario.setLocation(106, mario.getY());
        }else {
        	blockToCheck = new Point((int) marioBlockPosition.getX() - 1, (int) marioBlockPosition.getY());
            colorToCheck = mapImage.getRGB((int) blockToCheck.getX(), (int) blockToCheck.getY());
            colorToCheckPipe = mapImage.getRGB((int)blockToCheck.getX() - 1, (int)blockToCheck.getY());

            if (colorToCheck != air || colorToCheckPipe == pipeBodyRGB || colorToCheckPipe == pipeHeadRGB) {
            	if(colorToCheck != goombaRGB && koopaRGB != colorToCheck) {
            		int startingX = (int) blockToCheck.getX() * 48;
                    int startingY = (int) blockToCheck.getY() * 48;
                    Rectangle right = new Rectangle(startingX, startingY, 48, 48);

                    if (right.intersects(mario.getLeftBounds()) && mario.getVelX() < 0) {
                    	mario.setVelX(0);
                    }
                    int startingXpipe = (int) (blockToCheck.getX()+1)*48;

                    colorToCheck = mapImage.getRGB((int)blockToCheck.getX()-1, (int) blockToCheck.getY());

                    if(colorToCheck == pipeBodyRGB || colorToCheck == pipeHeadRGB) {
                    	right = new Rectangle(startingXpipe,startingY,48,48);
                        if(right.intersects(mario.getLeftBounds()) && mario.getVelX() < 0) mario.setVelX(0);
                    }
            	}

            }

        }

        //TODO: WE NEED TO TRANSFORM THIS IN METHODS BECAUSE IS SPAGHETTI

        for(Enemy enemy : mapCreator.getEnemies()){
            Point enemyPos = new Point(((int)enemy.getX()+24) /48, (int) (enemy.getY()+24) / 48);

            if(enemy.getBounds().intersects(mario.getBounds()) && !mario.isInvincible()) {

            	if(enemy.getTopBounds().intersects(mario.getBottomBounds()) && !mario.isJumping())
                    disposal.add(enemy);
                else if (mario.isSuper() || mario.isFire()) {
                    /* TODO: Logic */
                    mario.setInvincible(true);
                    mario.setFalling(true);
                    mario.setMarioMini();
                }
            	else if (!mario.isStar() && !mario.isSuper()){
                    /* TODO: Death screen */
                    //mario.remove();
                }
            }

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
                    	disposal.add(enemy);
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


        //BOOSTS


        for(Boost boost : mapCreator.getBoosts()){
            Point boostPos = new Point(((int)boost.getX()+24) /48, (int) (boost.getY()+24) / 48);

            if(boost.getBounds().intersects(mario.getBounds())) {
            	
            	if(boost.getType() == BoostType.superMushroom){
                    mario.setY(mario.getY()-48);
                    mario.setFalling(true);
                    MarioForm temp = new MarioForm(mario.getAnimation(),true,false,false);
                    mario.setMarioForm(temp);
                    mario.setMarioBig();
                    mario.getMarioForm().setSuper(true);
                }
            	
            	if(boost.getType() == BoostType.fireFlower){
                    mario.setMarioFire();
                    mario.getMarioForm().setFire(true);
                }
            	
            	if(boost.getType() == BoostType.starMan) {
            		mario.setMarioStar();
            		mario.getMarioForm().setStar(true);
            	}

            	if(boost.getType() == BoostType.mushroom1Up)
            		System.out.println("mushroom1Up");
            	
        		disposal.add(boost);
            }

            //Checks if the boost has a block under them
            blockToCheck = new Point(boostPos.x,boostPos.y+1);
            colorToCheck = mapImage.getRGB(blockToCheck.x,blockToCheck.y);
            if(colorToCheck != goombaRGB && colorToCheck != koopaRGB){

                if(checkCollisionRGB(colorToCheck)){
                    Rectangle under = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,48,48);
                    if(boost.getBottomBounds().intersects(under)){
                    	boost.setFalling(false);
                    	boost.setJumping(false);
                    	boost.setVelY(0);
                    }
                }else if(colorToCheck == dead) {
                	boost.setFalling(false);
                    	//System.out.println("dead");
                }else if(colorToCheck == air){
                	boost.setFalling(true);
                    	//System.out.println(enemy.getVelY());
                    }
            }

            //Checks if the boost has a block above them
            blockToCheck = new Point(boostPos.x,boostPos.y+1);
            colorToCheck = mapImage.getRGB(blockToCheck.x,blockToCheck.y);
            Rectangle above = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,48,48);
            if(colorToCheck != air && colorToCheck != goombaRGB && colorToCheck != koopaRGB){
                if(boost.getTopBounds().intersects(above)){
                	boost.setFalling(true);
                	boost.setJumping(false);
                	boost.setVelY(0);
                }
            }

            //Checks if the boost has a block at the right
            blockToCheck = new Point(boostPos.x+1,boostPos.y);
            colorToCheck = mapImage.getRGB(blockToCheck.x,blockToCheck.y);
            Rectangle right = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,48,48);
            if(colorToCheck != air && colorToCheck != goombaRGB && colorToCheck != koopaRGB){
                if(boost.getRightBounds().intersects(right)){
                	boost.setVelX(-boost.getVelX());
                }
            }

            //Checks if the boost has a block at the left
            blockToCheck = new Point(boostPos.x-2,boostPos.y);
            colorToCheck = mapImage.getRGB(blockToCheck.x,blockToCheck.y);

            if(blockToCheck.x == 0) boost.setVelX(-boost.getVelX());

            if(colorToCheck != air && colorToCheck != goombaRGB && colorToCheck != koopaRGB){
            	if(colorToCheck == pipeBodyRGB){

            		Rectangle left = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,96,48);
            		if(boost.getLeftBounds().intersects(left)) boost.setVelX(-boost.getVelX());

            	}else {
            		blockToCheck = new Point(boostPos.x-2,boostPos.y);
            		Rectangle left = new Rectangle(blockToCheck.x*48,blockToCheck.y*48,60,48);

                    if(boost.getLeftBounds().intersects(left)){
                    	boost.setVelX(-boost.getVelX());
                    }
                }
            }
        }

        for(GameObject object : disposal) {
            if (object instanceof Boost) map.getBoosts().remove((Boost) object);
            if (object instanceof Enemy) map.getEnemies().remove((Enemy) object);
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
