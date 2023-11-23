package view.screens;

import control.ButtonAction;

/**
 * The main menu screen handling the
 * state of the selected action.
 *
 * @version 1.0.0
 */
public class MainMenu {
    private int lineNumber;

    public MainMenu() {
        lineNumber = 0;
    }

    /**
     * Updates the current user selection on the screen.
     *
     * @param input Whether the selection is up or down.
     */
    public void changeSelection(ButtonAction input) {
        if (input == ButtonAction.SELECTION_DOWN) {
            if (lineNumber == 3) lineNumber = 0;
            else lineNumber++;
        } else {
            if (lineNumber == 0) lineNumber = 3;
            else lineNumber--;
        }
    }

    /* ---------- Getters / Setters ---------- */

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
