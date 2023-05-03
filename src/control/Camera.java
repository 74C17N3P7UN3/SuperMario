package control;

/**
 * The camera that follows the player.
 *
 * @author TacitNeptune
 * @version 0.1.1
 */
public class Camera {
    private double x, y;

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
    public void moveCam(double xAmount, double yAmount) {
        x += xAmount;
        y += yAmount;
    }

    /* ---------- Getters / Setters ---------- */

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
