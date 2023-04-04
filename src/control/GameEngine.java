package control;

/**
 * The core class of the program. It's responsible for handling the
 * initialization and synchronization of the other threads. It also
 * provides some runtime checks that make up the whole game's brain.
 *
 * @author TheInfernalNick
 * @version 0.0.1
 */
public class GameEngine implements Runnable {
    private final static int WIDTH = 1920, HEIGHT = 1080;

    private SoundManager soundManager;

    public GameEngine() {
        soundManager = new SoundManager();
    }

    @Override
    public void run() {
        //
    }
}
