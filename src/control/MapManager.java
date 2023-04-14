package control;

import model.Map;
import model.hero.Mario;
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
     * @param mapName     The name of the map to be loaded.
     * @return If the map was created successfully.
     */
    public boolean createMap(ImageLoader imageLoader, String mapName) {
        MapCreator mapCreator = new MapCreator(imageLoader);
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
