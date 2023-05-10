package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles all the key presses that the player performs to
 * then notify the performed action to the {@link GameEngine}.
 *
 * @version 1.0.0
 */
public class InputManager implements KeyListener {
    private final GameEngine engine;

    public InputManager(GameEngine engine) {
        this.engine = engine;
    }

    /**
     * Signals the {@link GameEngine} with the
     * currently pressed {@link ButtonAction}.
     *
     * @param input The action performed to be handled
     *              by the {@link GameEngine}.
     */
    private void notifyInput(ButtonAction input) {
        if (input != ButtonAction.NO_ACTION) engine.receiveInput(input);
    }

    /**
     * Handles a key being pressed.
     *
     * @param e The event to be processed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        ButtonAction currentAction = ButtonAction.NO_ACTION;

        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W || code == KeyEvent.VK_SPACE) currentAction = ButtonAction.JUMP;
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) currentAction = ButtonAction.CROUCH;
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) currentAction = ButtonAction.M_RIGHT;
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) currentAction = ButtonAction.M_LEFT;

        if (code == KeyEvent.VK_Z) currentAction = ButtonAction.FIRE;
        if (code == KeyEvent.VK_X) currentAction = ButtonAction.RUN;
        if (code == KeyEvent.VK_C) currentAction = ButtonAction.CHEAT;

        if (code == KeyEvent.VK_ENTER) currentAction = ButtonAction.ENTER;

        notifyInput(currentAction);
    }

    /**
     * Handles a key being released.
     *
     * @param e The event to be processed.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_C || code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_LEFT)
            notifyInput(ButtonAction.ACTION_COMPLETED);
    }

    /* ---------- Not needed implemented methods ---------- */

    @Override
    public void keyTyped(KeyEvent e) {}
}
