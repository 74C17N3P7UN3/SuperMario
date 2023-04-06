package control;

import javax.sound.sampled.Clip;

import static utils.SoundImporter.loadTrack;

/**
 * Responsible for retrieving and playing the
 * requested tracks from the media's package.
 *
 * @author TacitNeptune
 * @version 0.1.0
 */
public class SoundManager {
    private final Clip themeClip;
    private long clipTime;

    public SoundManager() {
        themeClip = loadTrack("theme");
    }

    /**
     * Resumes the theme's song playing in the background.
     */
    public void resumeTheme() {
        themeClip.setMicrosecondPosition(clipTime);
        themeClip.start();
    }

    /**
     * Pauses the theme's song playing in the background.
     */
    public void pauseTheme() {
        clipTime = themeClip.getMicrosecondPosition();
        themeClip.stop();
    }

    /**
     * Restarts the theme's song playing in the background.
     */
    public void restartTheme() {
        clipTime = 0;
        resumeTheme();
    }

    /* ---------- Play single sounds ---------- */

    public void playCoin() {
        loadTrack("coin").start();
    }
}
