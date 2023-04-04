package control;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

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
        themeClip = getClip(loadAudio("theme"));
    }

    /**
     * Returns the {@link AudioInputStream} of a given
     * track name, fetched from the media's package.
     *
     * @param name The name of the track to be loaded.
     * @return {@link AudioInputStream} if the track was
     * found, {@code null} otherwise.
     */
    private AudioInputStream loadAudio(String name) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/media/audio/" + name + ".wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            return AudioSystem.getAudioInputStream(bufferedIn);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    /**
     * Returns the {@link Clip} of a given track
     * {@link AudioInputStream}, obtained using
     * {@link #loadAudio} with a given name.
     *
     * @param stream The {@link AudioInputStream} of the track.
     * @return {@link Clip} if the {@link AudioInputStream} is
     * valid, {@code null} otherwise.
     */
    private Clip getClip(AudioInputStream stream) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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
}
