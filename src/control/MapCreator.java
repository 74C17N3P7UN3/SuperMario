package control;

import model.brick.Block;
import model.brick.Brick;
import model.brick.GroundBrick;
import model.brick.OrdinaryBrick;
import model.brick.PipeBody;
import model.brick.PipeHead;
import model.brick.SurpriseBrick;
import model.Map;
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
    private ImageLoader imageLoader;

    private BufferedImage backgroundImage;
    private BufferedImage ordinaryBrick, surpriseBrick, groundBrick, block;
    private BufferedImage pipeHead, pipeBody;

    public MapCreator(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        this.backgroundImage = ImageImporter.loadImage("background");

        BufferedImage sprite = ImageImporter.loadImage("sprite");
        this.ordinaryBrick = imageLoader.getImage(sprite, 1, 1, 48, 48);
        this.surpriseBrick = imageLoader.getImage(sprite, 2, 1, 48, 48);
        this.block = imageLoader.getImage(sprite, 2, 2, 48, 48);
        this.groundBrick = imageLoader.getImage(sprite, 2, 5, 48, 48);
        this.pipeHead = imageLoader.getImage(sprite, 3, 1, 96, 48);
        this.pipeBody = imageLoader.getImage(sprite, 3, 2, 96, 48);
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

        int mario = new Color(160, 160, 160).getRGB();

        int ordinaryBrick = new Color(237, 28, 36).getRGB();
        int surpriseBrick = new Color(163, 73, 164).getRGB();
        int groundBrick = new Color(237, 28, 36).getRGB();
        int block = new Color(127, 127, 127).getRGB();
        int pipeHead = new Color(34, 177, 76).getRGB();
        int pipeBody = new Color(181, 230, 29).getRGB();

        for (int x = 0; x < mapImage.getWidth(); x++) {
            for (int y = 0; y < mapImage.getHeight(); y++) {

                int currentPixel = mapImage.getRGB(x, y);
                int xLocation = x * 48;
                int yLocation = y * 48;

                if (currentPixel == ordinaryBrick) {
                    Brick brick = new OrdinaryBrick(xLocation, yLocation, this.ordinaryBrick);
                    createdMap.addBrick(brick);
                }
                if (currentPixel == surpriseBrick) {
                    Brick brick = new SurpriseBrick(xLocation, yLocation, this.surpriseBrick);
                    createdMap.addBrick(brick);
                }
                if (currentPixel == groundBrick) {
                    Brick brick = new GroundBrick(xLocation, yLocation, this.ordinaryBrick);
                    createdMap.addBrick(brick);
                }
                if (currentPixel == block) {
                    Brick brick = new Block(xLocation, yLocation, this.ordinaryBrick);
                    createdMap.addBrick(brick);
                }
                if (currentPixel == pipeHead) {
                    Brick brick = new PipeHead(xLocation, yLocation, this.pipeHead);
                    createdMap.addBrick(brick);
                }
                if (currentPixel == pipeBody) {
					Brick brick = new PipeBody(xLocation, yLocation, this.pipeBody);
					createdMap.addBrick(brick);
				}
            }
        }

		return createdMap;
	}
}
