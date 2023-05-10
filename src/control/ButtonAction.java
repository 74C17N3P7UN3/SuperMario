package control;

/**
 * The states that can be inputted.
 *
 * @version 1.0.0
 */
public enum ButtonAction {
    /** When the key gets released, the action needs to be completed. */
    ACTION_COMPLETED,
    /** A special key for running ultra-fast. */
    CHEAT,
    /** Crouch in the pipe to enter it. */
    CROUCH,
    /** Enter key is used to reset the game when it ends. */
    ENTER,
    /** Lets Mario spawn a fireball in the facing direction. */
    FIRE,
    /** A standard jump performed by Mario. */
    JUMP,
    /** Moves left horizontally. */
    M_LEFT,
    /** Moves right horizontally. */
    M_RIGHT,
    /** When no valid key is inputted, it gets ignored. */
    NO_ACTION,
    /** Lets mario run a little bit faster. */
    RUN,
}
