package control;

import model.Map;
import utils.ImageImporter;
import view.ImageLoader;
import view.UIManager;

import javax.swing.*;
import java.awt.*;

/**
 * The core class of the program. It's responsible for handling the
 * initialization and synchronization of the other threads. It also
 * provides some runtime checks that make up the whole game's brain.
 *
 * @author TheInfernalNick
 * @version 0.1.0
 */
public class GameEngine implements Runnable {
    private final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static int WIDTH = ((int) screenSize.getWidth()) - 80;
    private final static int HEIGHT = 720;

    private final Camera camera;
    private final ImageLoader imageLoader;
    private final InputManager inputManager;
    private final MapManager mapManager;
    private final SoundManager soundManager;
    private final UIManager uiManager;

    private GameStatus gameStatus;
    private Thread thread;
    private boolean isRunning;

    public GameEngine() {
        camera = new Camera();
        imageLoader = new ImageLoader();
        inputManager = new InputManager(this);
        mapManager = new MapManager();
        soundManager = new SoundManager();
        uiManager = new UIManager(this, WIDTH, HEIGHT);

        gameStatus = GameStatus.START_SCREEN;

        // Prepare the frame
        JFrame frame = new JFrame("Super Mario Bros");
        frame.setIconImage(ImageImporter.loadLogo());
        frame.add(uiManager);
        frame.pack();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.addKeyListener(inputManager);

        // Start the thread execution
        start();
    }

    private synchronized void start() {
        if (isRunning) return;

        isRunning = true;
        thread = new Thread(this);
        createMap("map-01");
        thread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (isRunning && !thread.isInterrupted()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                if (gameStatus == GameStatus.RUNNING)
                    gameLoop();
                delta--;
            }

            render();
        }
    }

    /**
     * Creates the selected map and sets
     * the game status to {@code running}.
     *
     * @param mapName The name of the map to be loaded.
     * @see GameStatus
     */
    private void createMap(String mapName) {
        boolean loaded = mapManager.createMap(imageLoader, mapName);

        if (loaded) {
            setGameStatus(GameStatus.RUNNING);
            soundManager.restartTheme();
        } else setGameStatus(GameStatus.START_SCREEN);
    }

    /**
     * Draws the Map calling {@link Map#drawMap}.
     *
     * @param g2D The Graphics engine drawing the map.
     */
    public void drawMap(Graphics2D g2D) {
        mapManager.drawMap(g2D);
    }

    /**
     * Runs the game until the game is over or the player dies.
     */
    private void gameLoop() {
        //
    }

    /**
     * Handles all the input received by the player. It checks for the
     * game state and currently selected screen to determine which action
     * needs to be executed by the {@link GameEngine}.
     *
     * @param input The inputted key-press.
     */
    public void receiveInput(ButtonAction input) {
        //
    }

    /**
     * Renders the current frame.
     */
    private void render() {
        uiManager.repaint();
    }

    /**
     * Plays a specific sound by the given name.
     *
     * @param soundName The name of the sound to be played.
     */
    public void playSound(String soundName) {
        soundManager.playSound(soundName);
    }

    /* ---------- Getters / Setters ---------- */

    public Point getCameraPosition() {
        return new Point((int) camera.getX(), (int) camera.getY());
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
