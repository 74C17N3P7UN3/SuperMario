package control;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundManager {
    private Clip themeClip;
    private long clipTime;

    public SoundManager() {
        themeClip = getClip(loadAudio("theme"));
    }

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

    public void resumeTheme(){
        themeClip.setMicrosecondPosition(clipTime);
        themeClip.start();
    }

    public void pauseTheme(){
        clipTime = themeClip.getMicrosecondPosition();
        themeClip.stop();
    }

    public void restartTheme() {
        clipTime = 0;
        resumeTheme();
    }
}
