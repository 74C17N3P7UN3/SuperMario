package control;

import model.Map;
import model.enemy.Enemy;
import model.hero.Mario;
import utils.ImageImporter;
import view.UIManager;

import javax.swing.*;
import java.awt.*;

/**
 * The core class of the program. It's responsible for handling the
 * initialization and synchronization of the other threads. It also
 * provides some runtime checks that make up the whole game's brain.
 *
 * @version 0.2.1
 */
public class GameEngine implements Runnable {
    private final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public final static int HEIGHT = 720; // Height is fixed because of the map's size
    public final static int WIDTH = ((int) screenSize.getWidth()) - 80;

    private final Camera camera;
    private final InputManager inputManager;
    private final MapManager mapManager;
    private final SoundManager soundManager;
    private final UIManager uiManager;

    private GameStatus gameStatus;
    private Thread thread;
    private boolean isRunning;
    private int coins, time, lifes;

    public GameEngine() {
        camera = new Camera();
        inputManager = new InputManager(this);
        mapManager = new MapManager();
        soundManager = new SoundManager();
        uiManager = new UIManager(this, HEIGHT, WIDTH);

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

    /**
     * The initializer method of the game engine,
     * which calls the {@link Thread#start()} method.
     */
    private synchronized void start() {
        if (isRunning) return;

        // FIXME: Temporary until we add the map selector
        createMap("map-01");

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

        long lastTimeCheck = 0, lastTimeInvincible = 0, lastTimeStar = 0;

        while (isRunning && !thread.isInterrupted()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (mapManager.getMario().isInvincible()) {
                if (lastTimeInvincible == 0) lastTimeInvincible = now / 1000000000;
                if (now / 1000000000 - lastTimeInvincible >= 2) {
                    mapManager.getMario().setInvincible(false);
                    lastTimeInvincible = 0;
                }
            }
            if (mapManager.getMario().isBabyStar() || mapManager.getMario().isStar()) {
                if (lastTimeStar == 0) lastTimeStar = now / 1000000000;
                if (now / 1000000000 - lastTimeStar >= 10) {
                    if (mapManager.getMario().isBabyStar()) mapManager.getMario().setMarioMini();
                    if (mapManager.getMario().isFire()) mapManager.getMario().setMarioFire();
                    else if (mapManager.getMario().isSuper()) mapManager.getMario().setMarioSuper();
                    lastTimeStar = 0;
                }
            }

            if (lastTimeCheck == 0) lastTimeCheck = now / 1000000000;
            if (now / 1000000000 - lastTimeCheck >= 1) {
                time--;
                lastTimeCheck = 0;
            }

            while (delta > 0) {
                if (gameStatus == GameStatus.RUNNING) gameLoop();

                delta--;
                render();
            }
        }
    }

    /**
     * Creates the selected map and sets the
     * {@link GameStatus} to {@code running}.
     *
     * @param mapName The name of the map to be loaded.
     */
    private void createMap(String mapName) {
        boolean loaded = mapManager.createMap(mapName, this);
        time = 120;
        coins = 0;
        if(gameStatus == GameStatus.START_SCREEN || gameStatus == GameStatus.GAME_OVER || gameStatus == GameStatus.MISSION_PASSED)lifes = 3;
        
        if (loaded) {
            setGameStatus(GameStatus.RUNNING);
            soundManager.restartTheme();
        } else setGameStatus(GameStatus.START_SCREEN);
    }

    /**
     * Draws the Map calling {@link Map#drawMap(Graphics2D)}.
     *
     * @param g2D The Graphics engine drawing the map.
     */
    public void drawMap(Graphics2D g2D) {
        mapManager.drawMap(g2D);
    }

    /**
     * Runs the game until the game is over or the player dies.
     * Contains all the game logic and movement checks.
     */
    private void gameLoop() {
        updateCamera();
        updateCollisions(this);
        updateLocations();

        Mario mario = mapManager.getMario();
        for (Enemy enemy : mapManager.getMap().getEnemies())
            if (enemy.getX() < camera.getX() + GameEngine.WIDTH && mario.getX() < 10992)
                if (enemy.getVelX() == 0) enemy.setVelX(-3);

        if (mario.getX() >= 12140) {
            camera.setX(7848 - 600);
            mario.pipeTeleport(7848, 384);
        }

        if (mario.getX() > 9792 && mario.getX() < 10992) {
            mario.setVelX(0);
            mario.setX(9792);
            mario.jump();
        }
        if (mario.getX() == 9792 && !mario.isJumping() && !mario.isFalling()) gameStatus = GameStatus.MISSION_PASSED;
        if (time == 0) gameStatus = GameStatus.OUT_OF_TIME;
    }

    /**
     * Plays a specific sound by the given name.
     *
     * @param soundName The name of the sound to be played.
     */
    public static void playSound(String soundName) {
        SoundManager.playSound(soundName);
    }

    /**
     * Handles all the input received by the player. It checks for the
     * game state and currently selected screen to determine which action
     * needs to be executed by the {@link GameEngine}.
     *
     * @param input The inputted key-press.
     */
    public void receiveInput(ButtonAction input) {
        if (mapManager.getEndPoint().isTouched() && input != ButtonAction.ENTER) return;

        Mario mario = mapManager.getMario();

        if (input == ButtonAction.JUMP)
            mario.jump();
        if (input == ButtonAction.CROUCH) {
            if (mario.getX() >= 2736 && mario.getX() <= 2784) {
                camera.setX(10992 + ((1920 - WIDTH) / 2));
                mario.pipeTeleport(11616, 96);
            }
        }
        if (input == ButtonAction.M_RIGHT)
            mario.move(true, camera);
        if (input == ButtonAction.M_LEFT)
            mario.move(false, camera);

        if (input == ButtonAction.FIRE && mario.isFire())
            mario.fire(mapManager);
        if (input == ButtonAction.RUN) {
            if (mario.getVelX() > 0) mario.setVelX(7.5);
            if (mario.getVelX() < 0) mario.setVelX(-7.5);
        }
        if (input == ButtonAction.CHEAT && mario.getVelX() >= 0)
            mario.setVelX(100);

        if (input == ButtonAction.ENTER && (gameStatus == GameStatus.GAME_OVER || gameStatus == GameStatus.MISSION_PASSED))
            reset();

        if (input == ButtonAction.ACTION_COMPLETED)
            mario.setVelX(0);
    }

    /**
     * Renders the current frame by repainting the JFrame.
     */
    private void render() {
        uiManager.repaint();
    }

    /**
     * Resets the game completely by resetting the camera
     * position, restarting the theme and sending the player
     * to the starting screen.
     */
    public void reset() {
        resetCamera();
        soundManager.restartTheme();
        mapManager.resetMap(this);
        createMap("map-01");

        setGameStatus(GameStatus.RUNNING);
    }

    /**
     * Resets the camera's position.
     */
    public void resetCamera() {
        camera.moveCam(-camera.getX(), -camera.getY());
    }

    /**
     * Updates the camera position based on the player's movement.
     * It also prevents the player from going back past the camera view.
     */
    private void updateCamera() {
        if (mapManager.getMario().getX() >= ((48 * 198) - 20)) return;

        Mario mario = mapManager.getMario();
        int marioVelX = (int) mario.getVelX();

        int shiftAmount = 0;
        if (marioVelX > 0 && mario.getX() - 600 > camera.getX())
            shiftAmount = marioVelX;

        camera.moveCam(shiftAmount, 0);

        // Also provide a check if mario goes out of the camera
        if (camera.getX() > mario.getX() - 96) {
            mario.setVelX(0);
            mario.setX(camera.getX() + 96);
        }
    }

    /**
     * Updates all entity/tiles locations
     * with {@link Map#updateLocations()}.
     */
    private void updateLocations() {
        mapManager.updateLocations();
    }

    /**
     * Check for all entity collisions with other entities
     * or blocks with {@link MapManager#checkCollisions}.
     */
    private void updateCollisions(GameEngine e) {
        mapManager.checkCollisions(e);
    }

    /* ---------- Getters / Setters ---------- */

    public Point getCameraPosition() {
        return new Point(camera.getX(), camera.getY());
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public int getTime() {
        return time;
    }
    
    public int getLifes() {
    	return lifes;
    }
    
    public void setLifes(int lifes) {
    	this.lifes = lifes;
    }
}
