package control;

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
    private final static int WIDTH = 1920, HEIGHT = 1080;

    private final Camera camera;
    private final ImageLoader imageLoader;
    private final InputManager inputManager;
    private final SoundManager soundManager;
    private final UIManager uiManager;

    private GameStatus gameStatus;
    private Thread thread;
    private boolean isRunning;

    public GameEngine() {
        camera = new Camera();
        imageLoader = new ImageLoader();
        inputManager = new InputManager(this);
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
     * Renders the current frame.
     */
    private void render() {
        uiManager.repaint();
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
