package view;

import control.ButtonAction;
import control.GameEngine;
import control.GameStatus;
import control.MapCreator;
import model.hero.Mario;
import utils.FontImporter;
import utils.ImageImporter;
import view.screens.MainMenu;
import view.screens.MultiplayerMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This manager is responsible for rendering all the
 * components, such as the map and the GUI on the screen.
 *
 * @version 1.1.0
 */
public class UIManager extends JPanel {
    private final GameEngine engine;

    private final MarioCursor marioCursor;
    private final MainMenu mainMenu;
    private final MultiplayerMenu multiplayerMenu;

    private boolean alreadyPlayed = false;

    public UIManager(GameEngine engine, int height, int width) {
        this.engine = engine;

        this.marioCursor = new MarioCursor();
        this.mainMenu = new MainMenu();
        this.multiplayerMenu = new MultiplayerMenu();

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
        else if (
            engine.getGameStatus() == GameStatus.MULTIPLAYER_LOBBY ||
            engine.getGameStatus() == GameStatus.MULTIPLAYER_HOST ||
            engine.getGameStatus() == GameStatus.MULTIPLAYER_JOIN
        ) showMultiplayerScreen(g2D);
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

                // Render Names
                g2D.setFont(FontImporter.loadFont(12));

                Mario mario = engine.getMapManager().getMap().getMario();
                float usernameX = (float) (mario.getX() - (g2D.getFontMetrics().stringWidth(mario.getUsername()) / 2) - camLocation.getX() + 24);
                g2D.drawString(mario.getUsername(), usernameX, (float) mario.getY() - 12);

                if (engine.getMapManager().isMultiplayer()) {
                    mario = engine.getMapManager().getMap().getNetMario();
                    usernameX = (float) (mario.getX() - (g2D.getFontMetrics().stringWidth(mario.getUsername()) / 2) - camLocation.getX() + 24);
                    g2D.drawString(mario.getUsername(), usernameX, (float) mario.getY() - 12);
                }
            }

            // To play the sound only once
            alreadyPlayed = false;
        }

        g2D.dispose();
    }

    /**
     * Changes the currently selected action on the corresponding screen.
     *
     * @param input Whether the selection is up or down.
     */
    public void changeSelectedAction(ButtonAction input) {
        if (engine.getGameStatus() == GameStatus.START_SCREEN) mainMenu.changeSelection(input);
    }

    /**
     * Confirms the currently selected action on the screen.
     */
    public void confirmSelectedAction() {
        if (engine.getGameStatus() == GameStatus.START_SCREEN) {
            int selection = mainMenu.getLineNumber();

            switch (selection) {
                case 0 -> engine.createMap("map-01", false);
                case 1 -> engine.setGameStatus(GameStatus.MULTIPLAYER_LOBBY);
                case 2 -> engine.setGameStatus(GameStatus.CREDITS_SCREEN);
                case 3 -> System.exit(0);
            }

            mainMenu.setLineNumber(0);
        }
    }

    /**
     * Draws a full-screen credits page.
     *
     * @param g2D The graphics engine.
     */
    private void showCreditsScreen(Graphics2D g2D) {
        BufferedImage screen = ImageImporter.loadImage("credits-screen");
        g2D.drawImage(screen, (GameEngine.WIDTH - 1920) / 2, 0, null);
    }

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
    private void showMultiplayerScreen(Graphics2D g2D) {
        BufferedImage screen = ImageImporter.loadImage("multiplayer-screen");
        g2D.drawImage(screen, (GameEngine.WIDTH - 1920) / 2, 0, null);

        g2D.setFont(FontImporter.loadFont(32));

        g2D.drawString(multiplayerMenu.getLocalHostIp(), 1056 + ((GameEngine.WIDTH - 1920) / 2), 358);
        if (engine.getGameStatus() == GameStatus.MULTIPLAYER_HOST)
            g2D.drawString(multiplayerMenu.getWaitingText(), 609 + ((GameEngine.WIDTH - 1920) / 2), 431);
        g2D.drawString(multiplayerMenu.getServerIp(), 1088 + ((GameEngine.WIDTH - 1920) / 2), 592);
    }

    /**
     * Draws the full-screen main menu page
     * and the mario-shaped selection cursor.
     *
     * @param g2D The graphics engine.
     */
    private void showStartScreen(Graphics2D g2D) {
        BufferedImage screen = ImageImporter.loadImage("start-screen");
        g2D.drawImage(screen, (GameEngine.WIDTH - 1920) / 2, 0, null);

        // Draw the Mario cursor
        BufferedImage mario = marioCursor.getCurrentStyle();

        int marioY = mainMenu.getLineNumber() < 3 ? 320 + 72 * mainMenu.getLineNumber() : 608;
        g2D.drawImage(mario, 710 + ((GameEngine.WIDTH - 1920) / 2), marioY, null);
    }

    /* ---------- Getters / Setters ---------- */

    public MultiplayerMenu getMultiplayerMenu() {
        return multiplayerMenu;
    }
}
