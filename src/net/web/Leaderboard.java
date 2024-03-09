package net.web;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

/**
 * This is the packet containing the retrieved
 * leaderboard object from the web server.
 *
 * @version 1.0.0
 */
@XmlRootElement
public class Leaderboard {
    @XmlElement (name="score")
    private ArrayList<Score> scores;

    // Empty constructor for JAXB
    public Leaderboard() {
        scores = new ArrayList<>();
    }

    /* ---------- Getters / Setters ---------- */

    public ArrayList<Score> getScores() {
        return scores;
    }
}
