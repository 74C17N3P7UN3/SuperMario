package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles all the key presses that the player performs.
 * It then notifies the performed action to the {@link GameEngine}
 *
 * @author TheInfernalNick
 * @version 0.1.0
 */
public class InputManager implements MouseListener, KeyListener {
    private GameEngine engine;

    InputManager(GameEngine engine) {
        this.engine = engine;
    }

    /**
     * Signals the {@link GameEngine} with the currently
     * pressed or clicked {@link ButtonAction}.
     *
     * @param action The action performed to be handled by
     *               the {@link GameEngine#receiveInput}.
     */
    private void notifyInput(ButtonAction action) {
        if (action != ButtonAction.NO_ACTION)
            engine.receiveInput(action);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        GameStatus status = engine.getGameStatus();
        ButtonAction currentAction = ButtonAction.NO_ACTION;

        if (keyCode == KeyEvent.VK_UP) {
            if (status == GameStatus.START_SCREEN || status == GameStatus.MAP_SELECTION)
                currentAction = ButtonAction.GO_UP;
            else currentAction = ButtonAction.JUMP;
        }
        else if (keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_W)
            currentAction = ButtonAction.JUMP;
        else if (keyCode == KeyEvent.VK_DOWN) {
            if (status == GameStatus.START_SCREEN || status == GameStatus.MAP_SELECTION)
                currentAction = ButtonAction.GO_DOWN;
            else currentAction = ButtonAction.CROUCH;
        }
        else if (keyCode == KeyEvent.VK_SHIFT || keyCode == KeyEvent.VK_S)
            currentAction = ButtonAction.CROUCH;
        else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)
            currentAction = ButtonAction.M_RIGHT;
        else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)
            currentAction = ButtonAction.M_LEFT;
        else if (keyCode == KeyEvent.VK_ENTER)
            currentAction = ButtonAction.SELECT;

        notifyInput(currentAction);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT)
            notifyInput(ButtonAction.ACTION_COMPLETED);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
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
    public void mouseReleased(MouseEvent e) {}
}
