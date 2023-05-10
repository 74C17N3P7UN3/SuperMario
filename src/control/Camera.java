package control;

/**
 * The camera that follows the player.
 *
 * @version 1.0.0
 */
public class Camera {
    private int x, y;

    public Camera() {
        x = 0;
        y = 0;
    }

    /**
     * Moves the camera by a certain amount.
     *
     * @param xAmount The horizontal increment.
     * @param yAmount The vertical increment.
     */
    public void moveCam(int xAmount, int yAmount) {
        x += xAmount;
        y += yAmount;
    }

    /* ---------- Getters / Setters ---------- */

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
}
