package control;

/**
 * The possible view states.
 *
 * @version 1.0.0
 */
public enum GameStatus {
    /** The player died and lost the game. */
    GAME_OVER,
    /** The player passed the level. */
    MISSION_PASSED,
    /** The player ran out of time. */
    OUT_OF_TIME,
    /** The game is running and the player is still playing. */
    RUNNING,
    /** The game is still in the loading phase. */
    START_SCREEN,
}
