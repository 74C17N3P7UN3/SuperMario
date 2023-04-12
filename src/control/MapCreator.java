package control;

import model.Map;
import model.brick.*;
import model.enemy.Enemy;
import model.enemy.Goomba;
import model.enemy.Koopa;
import utils.ImageImporter;
import view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Creates the {@link Map} by its given name.
 *
 * @author Bartolomuwu
 * @version 0.1.0
 */
public class MapCreator {
    private final ImageLoader imageLoader;

    private final BufferedImage backgroundImage;
    private final BufferedImage ordinaryBrick, surpriseBrick, groundBrick, block;
    private final BufferedImage pipeHead, pipeBody;
    private final BufferedImage goomba, koopa;

    public MapCreator(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        backgroundImage = ImageImporter.loadImage("background");

        BufferedImage sprite = ImageImporter.loadImage("sprite");
        block = imageLoader.getImage(sprite, 2, 2, 48, 48);
        groundBrick = imageLoader.getImage(sprite, 5, 2, 48, 48);
        ordinaryBrick = imageLoader.getImage(sprite, 1, 1, 48, 48);
        surpriseBrick = imageLoader.getImage(sprite, 2, 1, 48, 48);
        pipeHead = imageLoader.getImage(sprite, 3, 1, 96, 48);
        pipeBody = imageLoader.getImage(sprite, 3, 2, 96, 48);

        goomba = imageLoader.getImage(sprite, 1, 2, 48, 48);
        koopa = imageLoader.getImage(sprite, 0, 2, 48, 63);
    }

    /**
     * Returns the generated map by its name.
     *
     * @param mapName The name of the map to be generated.
     * @return The generated {@link Map} object.
     */
    public Map createMap(String mapName) {
        BufferedImage mapImage = ImageImporter.loadMap(mapName);

        Map createdMap = new Map(backgroundImage);

        int marioRGB = new Color(160, 160, 160).getRGB();

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

                Brick brick = null;
                Enemy enemy = null;
                if (currentPixel == blockRGB) brick = new Block(xLocation, yLocation, block);
                if (currentPixel == groundBrickRGB) brick = new GroundBrick(xLocation, yLocation, groundBrick);
                if (currentPixel == ordinaryBrickRGB) brick = new OrdinaryBrick(xLocation, yLocation, ordinaryBrick);
                if (currentPixel == surpriseBrickRGB) brick = new SurpriseBrick(xLocation, yLocation, surpriseBrick);
                if (currentPixel == pipeBodyRGB) brick = new PipeBody(xLocation, yLocation, pipeBody);
                if (currentPixel == pipeHeadRGB) brick = new PipeHead(xLocation, yLocation, pipeHead);

                if (currentPixel == goombaRGB) enemy = new Goomba(xLocation, yLocation, goomba);
                if (currentPixel == koopaRGB) enemy = new Koopa(xLocation, yLocation, koopa);

                if (brick != null) createdMap.addBrick(brick);
                if (enemy != null) createdMap.addEnemy(enemy);
            }
        }

        return createdMap;
    }
}
