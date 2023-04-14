package control;

import utils.SoundImporter;

import javax.sound.sampled.Clip;

/**
 * Responsible for retrieving and playing the
 * requested tracks from the media's package.
 *
 * @author TacitNeptune
 * @version 1.0.0
 */
public class SoundManager {
    private final Clip themeClip;
    private long clipTime;

    public SoundManager() {
        themeClip = SoundImporter.loadTrack("theme");
        themeClip.loop(Clip.LOOP_CONTINUOUSLY);
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

    /**
     * Plays a specific sound by the given name.
     *
     * @param soundName The name of the sound to be played.
     */
    public void playSound(String soundName) {
        SoundImporter.loadTrack(soundName).start();
    }
}
