package control;

import model.Map;
import model.hero.Fireball;
import model.hero.Mario;
import utils.ImageImporter;
import view.ImageLoader;
import view.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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
    private final static int HEIGHT = 720; // Height is fixed because of the map's size
    public final static int WIDTH = ((int) screenSize.getWidth()) - 80;

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
        mapManager = new MapManager(camera);
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
        long lastTimeInvincible = 0;

        // TODO: Remove
        long totalFrames = 0;
        long lastFpsCheck = 0;

        while (isRunning && !thread.isInterrupted()) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (mapManager.getMario().isInvincible()) {
            	if(lastTimeInvincible == 0)lastTimeInvincible = now / 1000000000;
            	if((System.nanoTime()/1000000000 - lastTimeInvincible) > 0.5) {
            		mapManager.getMario().setInvincible(false);
            		lastTimeInvincible = 0;
            	}
            }
                

            while (delta > 0) {
                if (gameStatus == GameStatus.RUNNING) gameLoop();
                delta--;
                render();

                // TODO: Remove
                totalFrames++;
                if (System.nanoTime() > lastFpsCheck + 1000000000) {
                    lastFpsCheck = System.nanoTime();
                    System.out.println(totalFrames);
                    totalFrames = 0;
                }
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
        boolean loaded = mapManager.createMap(imageLoader, mapName);

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

    public void drawDeadScreen(Graphics2D g2D) {
    	BufferedImage deadImage = ImageImporter.loadImage("game-over");
    	g2D.drawImage(deadImage, 0,0, null);
    }
    
    /**
     * Runs the game until the game is over or the player dies.
     */
    private void gameLoop() {
        updateCamera();
        updateCollisions(this);
        updateLocations();
    }

    /**
     * Plays a specific sound by the given name.
     *
     * @param soundName The name of the sound to be played.
     */
    public void playSound(String soundName) {
        soundManager.playSound(soundName);
    }

    /**
     * Handles all the input received by the player. It checks for the
     * game state and currently selected screen to determine which action
     * needs to be executed by the {@link GameEngine}.
     *
     * @param input The inputted key-press.
     */
    public void receiveInput(ButtonAction input) {
        Mario mario = mapManager.getMario();

        // TODO: Remove
        if (input == ButtonAction.CHEAT)
            mario.setVelX(100);
        if (input == ButtonAction.M_RIGHT)
            mario.move(true, camera);
        if (input == ButtonAction.M_LEFT)
            mario.move(false, camera);
        if (input == ButtonAction.JUMP)
            mario.jump(this);
        if (input == ButtonAction.ACTION_COMPLETED)
            mario.setVelX(0);
        if (input == ButtonAction.FIRE) {
        	if(mario.isFire())
        		mario.fire(mapManager);
        }
        if (input == ButtonAction.RUN) {
        	if(mario.getVelX() < 0)
        		mario.setVelX(-7.5);
        	if(mario.getVelX() > 0)
        		mario.setVelX(7.5);
        }
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
    private void reset() {
        resetCamera();
        soundManager.restartTheme();

        setGameStatus(GameStatus.START_SCREEN);
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
        Mario mario = mapManager.getMario();
        double marioVelX = mario.getVelX();

        double shiftAmount = 0;
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
     * or blocks with {@link MapManager#checkCollisions()}.
     */
    private void updateCollisions(GameEngine e) {
        mapManager.checkCollisions(e);
    }

    /* ---------- Getters / Setters ---------- */

    public Point getCameraPosition() {
        return new Point((int) camera.getX(), (int) camera.getY());
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public Mario getMario(){
        return mapManager.getMario();
    }
    
    public GameStatus getGameStatus() {
    	return this.gameStatus;
    }
    
    public int getWidth() {
    	return WIDTH;
    }
}
