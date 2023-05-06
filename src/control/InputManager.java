package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles all the key presses that the player performs to
 * then notify the performed action to the {@link GameEngine}.
 *
 * @author TheInfernalNick
 * @version 0.1.0
 */
public class InputManager implements KeyListener {
    private final GameEngine engine;

    InputManager(GameEngine engine) {
        this.engine = engine;
    }

    /**
     * Signals the {@link GameEngine} with the
     * currently pressed {@link ButtonAction}.
     *
     * @param input The action performed to be handled by
     *              the {@link GameEngine#receiveInput}.
     */
    private void notifyInput(ButtonAction input) {
        if (input != ButtonAction.NO_ACTION)
            engine.receiveInput(input);
    }

    /**
     * Handles a key being pressed.
     *
     * @param e The event to be processed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        ButtonAction currentAction = ButtonAction.NO_ACTION;

        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_SPACE)
            currentAction = ButtonAction.JUMP;
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
            currentAction = ButtonAction.M_RIGHT;
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
            currentAction = ButtonAction.M_LEFT;
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S)
        	currentAction = ButtonAction.PIPE_ENTER;

        if (keyCode == KeyEvent.VK_Z)
            currentAction = ButtonAction.FIRE;
        if (keyCode == KeyEvent.VK_X)
        	currentAction = ButtonAction.RUN;
        if (keyCode == KeyEvent.VK_C)
        	currentAction = ButtonAction.CHEAT;

        if (keyCode == KeyEvent.VK_ENTER)
        	currentAction = ButtonAction.ENTER;
        
        notifyInput(currentAction);
    }

    /**
     * Handles a key being released.
     *
     * @param e The event to be processed.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_C || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT)
            notifyInput(ButtonAction.ACTION_COMPLETED);
    }

    /* ---------- Not needed implemented methods ---------- */

    @Override
    public void keyTyped(KeyEvent e) { }
}
