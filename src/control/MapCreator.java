package control;

import model.EndFlag;
import model.Map;
import model.boost.Boost;
import model.brick.*;
import model.enemy.Goomba;
import model.hero.Mario;
import utils.ImageImporter;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Creates the {@link Map} by its given name.
 *
 * @version 0.2.0
 */
public class MapCreator {
    private Map createdMap;
    private BufferedImage mapImage;

    public final static BufferedImage sprite = ImageImporter.loadImage("sprite");

    public final static BufferedImage end = ImageLoader.getImage(sprite, 2, 2, 48, 48);
    public final static BufferedImage fireball = ImageLoader.getImage(sprite, 0, 4, 25, 22);

    public final static BufferedImage block = ImageLoader.getImage(sprite, 2, 3, 48, 48);
    public final static BufferedImage groundBrick = ImageLoader.getImage(sprite, 0, 1, 48, 48);
    public final static BufferedImage groundBrickBlue = ImageLoader.getImage(sprite, 1, 1, 48, 48);
    public final static BufferedImage ordinaryBrick = ImageLoader.getImage(sprite, 0, 0, 48, 48);
    public final static BufferedImage ordinaryBrickBlue = ImageLoader.getImage(sprite, 1, 0, 48, 48);
    public final static BufferedImage surpriseBrick = ImageLoader.getImage(sprite, 2, 0, 48, 48);
    public final static BufferedImage emptySurpriseBrick = ImageLoader.getImage(sprite, 2, 1, 48, 48);

    public final static BufferedImage pipeBodyH = ImageLoader.getImage(sprite, 3, 1, 96, 48);
    public final static BufferedImage pipeHeadH = ImageLoader.getImage(sprite, 3, 0, 96, 48);
    public final static BufferedImage pipeBodyV = ImageLoader.getImage(sprite, 4, 2, 48, 96);
    public final static BufferedImage pipeHeadV = ImageLoader.getImage(sprite, 3, 2, 48, 96);

    public final static BufferedImage goombaLeft = ImageLoader.getImage(sprite, 0, 3, 48, 48);
    public final static BufferedImage goombaRight = ImageLoader.getImage(sprite, 1, 3, 48, 48);

    public final static BufferedImage coin = ImageLoader.getImage(sprite, 0, 2, 48, 48);
    public final static BufferedImage coinBlue = ImageLoader.getImage(sprite, 1, 2, 48, 48);
    public final static BufferedImage fireFlower = ImageLoader.getImage(sprite, 3, 4, 48, 48);
    public final static BufferedImage heartMushroom = ImageLoader.getImage(sprite, 2, 4, 48, 48);
    public final static BufferedImage star = ImageLoader.getImage(sprite, 4, 4, 48, 48);
    public final static BufferedImage superMushroom = ImageLoader.getImage(sprite, 1, 4, 48, 48);
    
    public final static BufferedImage voidImage = ImageLoader.getImage(sprite, 0, 2, 1, 1);

    public MapCreator() {}

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
        int groundBrickBlueRGB = new Color(112, 146, 190).getRGB();
        int ordinaryBrickRGB = new Color(185, 122, 87).getRGB();
        int ordinaryBrickBlueRGB = new Color(0, 162, 232).getRGB();
        int surpriseBrickRGB = new Color(163, 73, 164).getRGB();

        int pipeBodyHRGB = new Color(181, 230, 29).getRGB();
        int pipeHeadHRGB = new Color(34, 177, 76).getRGB();
        int pipeBodyVRGB = new Color(0, 212, 145).getRGB();
        int pipeHeadVRGB = new Color(0, 151, 104).getRGB();

        int goombaRGB = new Color(63, 72, 204).getRGB();

        int coinBlueRGB = new Color(153, 217, 234).getRGB();

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
                if (currentPixel == groundBrickBlueRGB) brick = new GroundBrickBlue(xLocation, yLocation, groundBrickBlue);
                if (currentPixel == ordinaryBrickRGB) brick = new OrdinaryBrick(xLocation, yLocation, ordinaryBrick);
                if (currentPixel == ordinaryBrickBlueRGB) brick = new OrdinaryBrickBlue(xLocation, yLocation, ordinaryBrickBlue);
                if (currentPixel == surpriseBrickRGB) {
                    // Disguised SurpriseBricks
                	brick = new SurpriseBrick(xLocation, yLocation, surpriseBrick);
                    if (yLocation == 432 && (xLocation == 4512 || xLocation == 4848))
                    	brick.setStyle(ordinaryBrick);
                    if (yLocation == 384 && xLocation == 3072)
                    	brick.setStyle(voidImage);
                    if (yLocation == 432 && xLocation == 4512)
                    	((SurpriseBrick) brick).setBoost(5);
                }

                if (currentPixel == pipeBodyHRGB) brick = new PipeBody(xLocation, yLocation, pipeBodyH);
                if (currentPixel == pipeHeadHRGB) brick = new PipeHead(xLocation, yLocation, pipeHeadH);
                if (currentPixel == pipeBodyVRGB) brick = new PipeBody(xLocation, yLocation, pipeBodyV);
                if (currentPixel == pipeHeadVRGB) brick = new PipeHead(xLocation, yLocation, pipeHeadV);

                if (currentPixel == coinBlueRGB) brick = new CoinBlue(xLocation, yLocation, coinBlue);

                Goomba goomba = null;
                if (currentPixel == goombaRGB) {
                    goomba = new Goomba(xLocation, yLocation, goombaLeft);
                    goomba.setRightImage(goombaRight);
                }

                if (brick != null) createdMap.addBrick(brick);
                if (goomba != null) createdMap.addEnemy(goomba);
            }
        }

        return createdMap;
    }

    /* ---------- Getters / Setters ---------- */

    public ArrayList<Boost> getBoosts() {
        return createdMap.getBoosts();
    }
}
