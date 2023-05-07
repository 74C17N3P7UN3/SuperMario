package utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Objects;

/**
 * Util class responsible for importing audio tracks
 * from the media's package by their name.
 *
 * @version 1.0.0
 */
public class SoundImporter {
    /**
     * Returns the {@link Clip} of a given track by
     * its relative name inside the media's package.
     *
     * @param name The name of the track to be loaded.
     * @return The {@link Clip} of the requested track.
     */
    public static Clip loadTrack(String name) {
        return Objects.requireNonNull(getClip(getAudio(name)));
    }

    /**
     * Returns the {@link AudioInputStream} of a given
     * track name, fetched from the media's package.
     *
     * @param name The name of the track to be loaded.
     * @return {@link AudioInputStream} if the track was
     * found, {@code null} otherwise.
     */
    private static AudioInputStream getAudio(String name) {
        try {
            InputStream audioSrc = SoundImporter.class.getResourceAsStream("/media/audio/" + name + ".wav");
            assert audioSrc != null; // Makes sure that the loaded stream is not null
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            return AudioSystem.getAudioInputStream(bufferedIn);
        } catch (Exception ignored) {}

        return null;
    }

    /**
     * Returns the {@link Clip} of a given track
     * {@link AudioInputStream}, obtained using
     * {@link #getAudio} with a given name.
     *
     * @param stream The {@link AudioInputStream} of the track.
     * @return The {@link Clip} relative to the {@link AudioInputStream}
     */
    private static Clip getClip(AudioInputStream stream) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            return clip;
        } catch (Exception ignored) {}

        return null;
    }
}
