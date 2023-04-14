package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles all the key presses that the player performs to
 * then notify the performed action to the {@link GameEngine}.
 *
 * @author TheInfernalNick
 * @version 0.1.0
 */
public class InputManager implements KeyListener, MouseListener {
    private final GameEngine engine;

    InputManager(GameEngine engine) {
        this.engine = engine;
    }

    /**
     * Signals the {@link GameEngine} with the currently
     * pressed or clicked {@link ButtonAction}.
     *
     * @param input The action performed to be handled by the
     *               {@link GameEngine#receiveInput(ButtonAction)}.
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

        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT)
            notifyInput(ButtonAction.ACTION_COMPLETED);
    }

    /* ---------- Not needed implemented methods ---------- */

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}
