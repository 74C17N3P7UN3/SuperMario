package control;

import model.Map;
import view.ImageLoader;

import java.awt.*;

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

    public MapManager() {}

    /**
     * Creates the map using the {@link MapCreator}.
     *
     * @param imageLoader The loader responsible for
     *                    drawing the map in the creator.
     * @param mapName The name of the map to be loaded.
     * @return If the map was loaded successfully.
     */
    public boolean createMap(ImageLoader imageLoader, String mapName) {
        MapCreator mapCreator = new MapCreator(imageLoader);
        map = mapCreator.createMap(mapName);

        return map != null;
    }

    public void drawMap(Graphics2D g2){
        map.drawMap(g2);
    }
}
