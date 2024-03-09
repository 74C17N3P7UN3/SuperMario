package net.web;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the packet containing information
 * about the achieved score by the player.
 *
 * @version 1.0.0
 */
@XmlRootElement
public class Score {
    public final String username;
    public final int points;
    public final String timestamp;

    // Empty constructor for JAXB
    public Score() {
        this.username = "";
        this.points = 0;
        this.timestamp = currentTime();
    }

    public Score(String username, int points) {
        this.username = username;
        this.points = points;
        this.timestamp = currentTime();
    }

    /**
     * Creates a pretty-formatted
     * string of the current time.
     *
     * @return The current time string.
     */
    private String currentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();

        return formatter.format(date);
    }
}
