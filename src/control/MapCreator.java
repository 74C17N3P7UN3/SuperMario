package control;

import model.EndFlag;
import model.Map;
import model.brick.*;
import model.enemy.Enemy;
import model.enemy.Goomba;
import model.enemy.Koopa;
import model.hero.Mario;
import model.prize.Boost;
import utils.ImageImporter;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Creates the {@link Map} by its given name.
 *
 * @author Bartolomuwu
 * @version 0.1.0
 */
public class MapCreator {
    private Map createdMap;
    private BufferedImage mapImage;
    private Camera camera;

    private final BufferedImage end;
    private final BufferedImage block, groundBrick, ordinaryBrick, surpriseBrick, emptySurpriseBrick;
    private final BufferedImage pipeBody, pipeHead;
    private final BufferedImage goombaLeft, goombaRight, koopaLeft, koopaRight;
    private final BufferedImage coin, fireFlower, heartMushroom, star, superMushroom;
    private final BufferedImage voidBoost, fireball;

    public MapCreator(ImageLoader imageLoader, Camera camera) {
        BufferedImage sprite = ImageImporter.loadImage("sprite");

        end = imageLoader.getImage(sprite, 4, 0, 48, 48);

        block = imageLoader.getImage(sprite, 1, 1, 48, 48);
        groundBrick = imageLoader.getImage(sprite, 4, 1, 48, 48);
        ordinaryBrick = imageLoader.getImage(sprite, 0, 0, 48, 48);
        surpriseBrick = imageLoader.getImage(sprite, 1, 0, 48, 48);
        emptySurpriseBrick = imageLoader.getImage(sprite, 0, 1, 48, 48);

        pipeBody = imageLoader.getImage(sprite, 2, 1, 96, 48);
        pipeHead = imageLoader.getImage(sprite, 2, 0, 96, 48);

        goombaLeft = imageLoader.getImage(sprite, 1, 3, 48, 48);
        goombaRight = imageLoader.getImage(sprite, 4, 3, 48, 48);
        koopaLeft = imageLoader.getImage(sprite, 0, 2, 48, 64);
        koopaRight = imageLoader.getImage(sprite, 3, 2, 48, 64);

        coin = imageLoader.getImage(sprite, 0, 4, 48, 48);
        fireFlower = imageLoader.getImage(sprite, 3, 4, 48, 48);
        heartMushroom = imageLoader.getImage(sprite, 2, 4, 48, 48);
        star = imageLoader.getImage(sprite, 4, 4, 48, 48);
        superMushroom = imageLoader.getImage(sprite, 1, 4, 48, 48);

        voidBoost = imageLoader.getImage(sprite,4,2,48,48);
        fireball = imageLoader.getImage(sprite, 2, 3, 48, 48);
        
        this.camera = camera;
    }

    /**
     * Returns the generated map by its name.
     *
     * @param mapName The name of the map to be generated.
     * @return The generated {@link Map} object.
     */
    public Map createMap(String mapName) {
        createdMap = new Map(mapName);
        mapImage = ImageImporter.loadMap(mapName);

        int endRGB = new Color(195, 195, 195).getRGB();
        int marioRGB = new Color(255, 127, 39).getRGB();

        int blockRGB = new Color(127, 127, 127).getRGB();
        int groundBrickRGB = new Color(237, 28, 36).getRGB();
        int ordinaryBrickRGB = new Color(185, 122, 87).getRGB();
        int surpriseBrickRGB = new Color(163, 73, 164).getRGB();

        int pipeBodyRGB = new Color(181, 230, 29).getRGB();
        int pipeHeadRGB = new Color(34, 177, 76).getRGB();

        int goombaRGB = new Color(63, 72, 204).getRGB();
        int koopaRGB = new Color(255, 174, 201).getRGB();

        for (int x = 0; x < mapImage.getWidth(); x++) {
            for (int y = 0; y < mapImage.getHeight(); y++) {
                int currentPixel = mapImage.getRGB(x, y);
                int xLocation = x * 48;
                int yLocation = y * 48;

                if (currentPixel == endRGB) createdMap.setEndPoint(new EndFlag(xLocation - 24, yLocation, end));
                if (currentPixel == marioRGB) createdMap.setMario(new Mario(xLocation, yLocation));

                Brick brick = null;
                if (currentPixel == blockRGB) brick = new Block(xLocation, yLocation, block);
                if (currentPixel == groundBrickRGB) brick = new GroundBrick(xLocation, yLocation, groundBrick);
                if (currentPixel == ordinaryBrickRGB) brick = new OrdinaryBrick(xLocation, yLocation, ordinaryBrick);
                if (currentPixel == surpriseBrickRGB) {
                    if (yLocation == 432 && (xLocation == 4512 || xLocation == 4848))
                        brick = new SurpriseBrick(xLocation, yLocation, ordinaryBrick);
                    else brick = new SurpriseBrick(xLocation, yLocation, surpriseBrick);
                }

                if (currentPixel == pipeBodyRGB) brick = new PipeBody(xLocation, yLocation, pipeBody);
                if (currentPixel == pipeHeadRGB) brick = new PipeHead(xLocation, yLocation, pipeHead);

                Enemy enemy = null;
                if (currentPixel == goombaRGB) {
                    enemy = new Goomba(xLocation, yLocation, goombaLeft);
                    ((Goomba) enemy).setRightImage(goombaRight);
                    if(((Goomba)enemy).getX() > camera.getX() + GameEngine.WIDTH)
                    	((Goomba) enemy).setVelX(0);
                }
                if (currentPixel == koopaRGB) {
                    enemy = new Koopa(xLocation, yLocation, koopaLeft);
                    ((Koopa) enemy).setRightImage(koopaRight);
                    if(((Koopa)enemy).getX() > camera.getX() + GameEngine.WIDTH)
                    	((Koopa) enemy).setVelX(0);
                }

                if (brick != null) createdMap.addBrick(brick);
                if (enemy != null) createdMap.addEnemy(enemy);
            }
        }

        return createdMap;
    }

    /* ---------- Getters / Setters ---------- */

    public ArrayList<Boost> getBoosts() {
        return createdMap.getBoosts();
    }

    public ArrayList<Enemy> getEnemies() {
        return createdMap.getEnemies();
    }

    public BufferedImage getCoin() {
        return coin;
    }

    public BufferedImage getEmptySurpriseBrick() {
        return emptySurpriseBrick;
    }
    
    public BufferedImage getFireFlower() {
    	return fireFlower;
    }

    public BufferedImage getHeartMushroom() {
        return heartMushroom;
    }

    public BufferedImage getMapImage() {
        return mapImage;
    }

    public BufferedImage getStar() {
        return star;
    }

    public BufferedImage getSuperMushroom() {
        return superMushroom;
    }

    public BufferedImage getVoidBoost(){
        return voidBoost;
    }
    
    public BufferedImage getFireball() {
    	return fireball;
    }
}
