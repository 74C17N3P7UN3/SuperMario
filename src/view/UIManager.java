package view;

import control.GameEngine;
import control.GameStatus;
import control.MapCreator;
import utils.FontImporter;
import utils.ImageImporter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This manager is responsible for rendering all the
 * components, such as the map and the GUI on the screen.
 *
 * @version 0.1.1
 */
public class UIManager extends JPanel {
    GameEngine engine;

    public UIManager(GameEngine engine, int height, int width) {
        this.engine = engine;

        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setFont(FontImporter.loadFont(28));
        g2D.setColor(Color.WHITE);

        if (engine.getGameStatus() == GameStatus.GAME_OVER) {
            drawScreen(g2D, "game-over");
            engine.getSoundManager().pauseTheme();
        } else if (engine.getGameStatus() == GameStatus.MISSION_PASSED) {
            drawScreen(g2D, "game-won");
            engine.getSoundManager().pauseTheme();
        } else {
            Point camLocation = engine.getCameraPosition();
            g2D.translate(-camLocation.getX(), -camLocation.getY());
            engine.drawMap(g2D);
            g2D.translate(camLocation.getX(), camLocation.getY());

            // Render Coins
            g2D.drawImage(MapCreator.coin, 50, 30, 28, 28, null);
            g2D.drawString(String.valueOf(engine.getCoins()), 90, 55);

            // Render Time
            g2D.drawString(String.valueOf(engine.getTime()), GameEngine.WIDTH - 140, 55);
        }

        g2D.dispose();
    }

    /**
     * Draws a full-screen image instead of the map.
     *
     * @param g2D The graphics engine.
     * @param screenName The name of the screen to be loaded.
     */
    public void drawScreen(Graphics2D g2D, String screenName) {
        BufferedImage screen = ImageImporter.loadImage(screenName);
        g2D.drawImage(screen, 0, 0, null);
    }
}
