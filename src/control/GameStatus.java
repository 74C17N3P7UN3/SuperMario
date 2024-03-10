package control;

/**
 * The possible view states.
 *
 * @version 1.3.1
 */
public enum GameStatus {
    /** The credits screen. */
    CREDITS_SCREEN,
    /** The player died and lost the game. */
    GAME_OVER,
    /** The leaderboards screen. */
    LEADERBOARDS,
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
    /** The game is running and the player is playing. */
    RUNNING,
    /** The starting selection screen. */
    START_SCREEN,
    /** The single player username input screen. */
    USERNAME_SCREEN,
}
