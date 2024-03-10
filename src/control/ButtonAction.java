package control;

/**
 * The states that can be inputted.
 *
 * @version 1.0.1
 */
public enum ButtonAction {
    /** Complete whatever movement was in progress. */
    ACTION_COMPLETED,
    /** Allows Mario to move at supersonic speed. */
    CHEAT,
    /** Allows Mario to enter pipes from the top. */
    CROUCH,
    /** Select or confirm the current action. */
    ENTER,
    /** Go back or cancel the current action. */
    ESCAPE,
    /** Allows Mario to throw a fireball. */
    FIRE,
    /** Allows Mario to perform a jump. */
    JUMP,
    /** Allows Mario to move left. */
    M_LEFT,
    /** Allows Mario to move right. */
    M_RIGHT,
    /** The default ignored action state. */
    NO_ACTION,
    /** Allows Mario to run a little faster. */
    RUN,
    /** Indicates a negative selection input. */
    SELECTION_DOWN,
    /** Indicates a positive selection input. */
    SELECTION_UP,
}
