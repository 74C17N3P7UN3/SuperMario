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
    /** Enter key is used to confirm an action or restart the game. */
    ENTER,
    /** Escape key is used to exit or go back to the menu. */
    ESCAPE,
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
    /** Moves the graphic selection down. */
    SELECTION_DOWN,
    /** Moves the graphic selection up. */
    SELECTION_UP,
}
