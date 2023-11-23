package control;

/**
 * The possible view states.
 *
 * @version 1.1.0
 */
public enum GameStatus {
    /** The credits screen. */
    CREDITS_SCREEN,
    /** The player died and lost the game. */
    GAME_OVER,
    /** The player passed the level. */
    MISSION_PASSED,
    /** The player hosts the server and waits for connections. */
    MULTIPLAYER_HOST,
    /** The player tries to connect to a local server. */
    MULTIPLAYER_JOIN,
    /** The lobby where you wait for your teammate. */
    MULTIPLAYER_LOBBY,
    /** The player ran out of time. */
    OUT_OF_TIME,
    /** The game is running and the player is still playing. */
    RUNNING,
    /** The starting selection screen. */
    START_SCREEN,
}
