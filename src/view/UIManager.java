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
 * @version 1.0.0
 */
public class UIManager extends JPanel {
    private final GameEngine engine;

    private boolean alreadyPlayed = false;

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

        if (engine.getGameStatus() == GameStatus.START_SCREEN) showStartScreen(g2D);
        else if (engine.getGameStatus() == GameStatus.CREDITS_SCREEN) showCreditsScreen(g2D);
        else if (engine.getGameStatus() == GameStatus.MULTIPLAYER_LOBBY) showMultiplayerScreen(g2D);
        else if (engine.getGameStatus() == GameStatus.GAME_OVER) showEndingScreen(g2D, "game-over");
        else if (engine.getGameStatus() == GameStatus.MISSION_PASSED) showEndingScreen(g2D, "game-won");
        else if (engine.getGameStatus() == GameStatus.OUT_OF_TIME) showEndingScreen(g2D, "out-of-time");
        else {
            Point camLocation = engine.getCameraPosition();
            g2D.translate(-camLocation.getX(), -camLocation.getY());
            engine.drawMap(g2D);
            g2D.translate(camLocation.getX(), camLocation.getY());

            if (engine.isRunning()) {
                // Render Coins
                g2D.drawImage(MapCreator.coin, 50, 30, 28, 28, null);
                g2D.drawString(String.valueOf(engine.getMapManager().getMap().getCoins()), 90, 55);

                // Render Lives
                g2D.drawImage(MapCreator.heartMushroom, 50, 70, 28, 28, null);
                g2D.drawString(String.valueOf(engine.getMapManager().getMap().getLives()), 90, 95);

                // Render Time
                g2D.drawString(String.valueOf(engine.getMapManager().getMap().getTime()), GameEngine.WIDTH - 140, 55);
            }

            // To play the sound only once
            alreadyPlayed = false;
        }

        g2D.dispose();
    }

    /**
     * Draws a full-screen credits page.
     *
     * @param g2D The graphics engine.
     */
    private void showCreditsScreen(Graphics2D g2D) {}

    /**
     * Draws a full-screen ending page with the results of the run.
     *
     * @param g2D        The graphics engine.
     * @param screenName The name of the screen to be loaded.
     */
    public void showEndingScreen(Graphics2D g2D, String screenName) {
        if (!alreadyPlayed) {
            engine.pauseTheme();
            GameEngine.playSound(screenName);
            alreadyPlayed = true;
        }

        BufferedImage screen = ImageImporter.loadImage(screenName);
        g2D.drawImage(screen, (GameEngine.WIDTH - 1920) / 2, 0, null);

        // If the player won, show the score too
        if (screenName.equals("game-won")) {
            String points = "You scored: " + engine.getMapManager().getMap().getPoints() + " points!";
            g2D.drawString(points, (GameEngine.WIDTH - g2D.getFontMetrics().stringWidth(points)) / 2, 150);
        }
    }

    /**
     * Draws a full-screen multiplayer waiting lobby page.
     *
     * @param g2D The graphics engine.
     */
    private void showMultiplayerScreen(Graphics2D g2D) {}

    /**
     * Draws the full-screen selection main menu.
     *
     * @param g2D The graphics engine.
     */
    private void showStartScreen(Graphics2D g2D) {
        BufferedImage screen = ImageImporter.loadImage("start-screen");
        g2D.drawImage(screen, (GameEngine.WIDTH - 1920) / 2, 0, null);
    }
}
