package control;

import utils.SoundImporter;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Responsible for retrieving and playing the
 * requested tracks from the media's package.
 *
 * @version 1.1.0
 */
public class SoundManager {
    private final Clip themeClip;
    private long clipTime;

    public SoundManager() {
        themeClip = SoundImporter.loadTrack("theme");
        setClipVolume(themeClip, 0.6);
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
    public static void playSound(String soundName) {
        Clip clip = SoundImporter.loadTrack(soundName);
        setClipVolume(clip, 0.4);
        clip.start();
    }

    /**
     * Sets the volume of a given track
     * to the given gain percentage.
     *
     * @param clip The clip which the volume
     *             needs to be adjusted
     * @param gain The volume percentage
     */
    private static void setClipVolume(Clip clip, double gain) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(gain));
    }
}
