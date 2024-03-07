package view.screens;

import control.ButtonAction;

import java.util.ArrayList;
import java.util.List;

/**
 * The menu screen handling the fetching
 * of the scores from the web server.
 *
 * @version 1.0.0
 */
public class LeaderboardsMenu {
    private ArrayList<String> scores;
    private int range = 0;

    public void changeScoresRange(ButtonAction input) {
        int lastValidRange = scores.size() % 5 == 0 ? scores.size() - 5 : scores.size() - scores.size() % 5;

        if (input == ButtonAction.SELECTION_DOWN) {
            if (range == lastValidRange) range = 0;
            else range += 5;
        } else {
            if (range == 0) range = lastValidRange;
            else range -= 5;
        }
    }

    /**
     * Retrieves the scores from the web server
     * and sets a pretty-formatted string array.
     */
    public void fetchScores() {
        // TODO: Needs actual data fetching
        ArrayList<String> temp = new ArrayList<>();
        temp.add("1. Player_1         15320");
        scores = temp;
    }

    /**
     * Returns the 5 scores in the
     * currently selected 5-range set.
     *
     * @return A pretty-formatted string array
     * containing the 5-range sets of scores.
     */
    public List<String> getScoresInRange() {
        int lastValidRange = scores.size() % 5 == 0 ? scores.size() - 5 : scores.size() - scores.size() % 5;
        return scores.subList(range, range == lastValidRange ? scores.size() : range + 5);
    }
}